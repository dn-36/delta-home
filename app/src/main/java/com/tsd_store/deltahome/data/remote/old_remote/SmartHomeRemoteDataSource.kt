package com.tsd_store.deltahome.data.remote.old_remote

import android.util.Log
import com.module.core.network.chats.ChatResponseMessages
import com.module.core.network.chats.MessageData
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.channel.PusherEvent
import com.pusher.client.channel.SubscriptionEventListener
import com.tsd_store.deltahome.common.network.NetworkError
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.common.network.makeRequest
import com.tsd_store.deltahome.common.network.onSuccess
import com.tsd_store.deltahome.data.remote.old_remote.models.SmartHomeSnapshotDto
import io.ktor.http.HttpMethod
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive


class  SmartHomeRemoteDataSource(

    private val json: Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = false
    }
) {
    private val UI_CHAT_DEVICE_MANEGEMENT = "zf7dxyir-ex1mqi9j-kyh3qw5c"


    private val baseUrl:String = "https://delta.online/"

    private val CHAT = "_CHAT"

    suspend fun getSmartHomeSnapshotDto(): ResultNetwork<SmartHomeSnapshotDto, NetworkError> {

        val networkResult: ResultNetwork<ChatResponseMessages, NetworkError> =
            getListMassengers(UI_CHAT_DEVICE_MANEGEMENT)

        return when (networkResult) {

            is ResultNetwork.Error -> {
                // просто прокидываем сетевую ошибку
                ResultNetwork.Error(networkResult.error)
            }

            is ResultNetwork.Success -> {
                val response = networkResult.data
                val messages: List<MessageData>? = response.messages?.data

                // берём последнее сообщение с не пустым text
                val lastMessageWithText: MessageData? =
                    messages
                        ?.filter { !it.text.isNullOrBlank() }
                        ?.maxByOrNull { it.id }   // или lastOrNull(), если нужен именно порядок

                val jsonText = lastMessageWithText?.text

                if (jsonText.isNullOrBlank()) {
                    Log.e("SmartHomeRemoteDS", "No message with JSON text for chatId=$UI_CHAT_DEVICE_MANEGEMENT")
                    return ResultNetwork.Error(NetworkError.SERIALIZATION)
                }

                try {
                    val snapshot: SmartHomeSnapshotDto =
                        json.decodeFromString(jsonText)

                    ResultNetwork.Success(snapshot)
                } catch (e: SerializationException) {
                    Log.e("SmartHomeRemoteDS", "JSON parse error: $jsonText", e)
                    ResultNetwork.Error(NetworkError.SERIALIZATION)
                } catch (e: Throwable) {
                    Log.e("SmartHomeRemoteDS", "Unknown error parse snapshot", e)
                    ResultNetwork.Error(NetworkError.UNKNOWN)
                }
            }
        }
    }



    suspend fun sendMessage(
        chatUI: String,
        text: String,

    ): ResultNetwork<String, NetworkError> {



        val newMessageDto = NewMessageDto(
            chatUi = chatUI,
            feedbackUi = "",
            text = text,
            image = null,
            files = null
        )

        println("тело при отправке сообщения $newMessageDto")

        return makeRequest(
            url = "${baseUrl}api/message/",
            body = newMessageDto,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = "MESSANGER_SEND"
        )
    }


    private suspend fun getListMassengers(
        chatUI: String
    ): ResultNetwork<ChatResponseMessages, NetworkError> {

        var result: ResultNetwork<ChatResponseMessages, NetworkError>

        result = makeRequest(
            url = baseUrl + "api/chats/${chatUI}?page=1",
            body = null,
            method = HttpMethod.Get,
            client = NetworkClient.client,
            tag = CHAT + "_GET_MESSAGES"
        )

        result.onSuccess { response ->
            println("getListMassengers ${response.messages?.data?.map { it.feedback }}")
        }

        return result
    }

    // -------------------- SOCKET / PUSHER --------------------

    private val pusher: Pusher by lazy {
        Pusher(
            "3e61a977343ce3cea394",
            PusherOptions().apply { setCluster("eu") }
        ).also { it.connect() }
    }


    private val subscribed = mutableSetOf<Pair<String, String>>()


    suspend fun subscribeChanelChangeDataStateDevices(
        onNewSnapshot: (SmartHomeSnapshotDto) -> Unit
    ) {
        val channelName = "chat_channel_$UI_CHAT_DEVICE_MANEGEMENT"
        val eventName = "send-message-store" // подставь своё имя ивента, если другое

        val channel = ensureChannel(channelName)
        val key = channelName to eventName

        if (subscribed.contains(key)) return

        Log.d("SocketsDevicesApi", "Subscribe channel=$channelName event=$eventName")

        channel.bind(eventName, object : SubscriptionEventListener {
            override fun onEvent(event: PusherEvent?) {
                val rawData = event?.data ?: return
                Log.d("SocketsDevicesApi", "devices-state-changed raw: $rawData")

                try {
                    val snapshotJson = extractSnapshotJson(rawData)
                    if (snapshotJson == null) {
                        Log.e("SocketsDevicesApi", "No snapshot JSON inside event data")
                        return
                    }

                    val snapshot = json.decodeFromString<SmartHomeSnapshotDto>(snapshotJson)
                    onNewSnapshot(snapshot)

                } catch (e: SerializationException) {
                    Log.e("SocketsDevicesApi", "Failed to parse SmartHomeSnapshotDto", e)
                } catch (e: Throwable) {
                    Log.e("SocketsDevicesApi", "Unknown error on devices-state-changed", e)
                }
            }
        })

        subscribed += key
    }

    private fun ensureChannel(name: String): Channel {
        val existing = pusher.getChannel(name)
        return existing ?: pusher.subscribe(name)
    }


    private fun extractSnapshotJson(rawData: String): String? {
        return try {
            val element: JsonElement = json.parseToJsonElement(rawData)

            if (element is JsonObject) {
                // Вариант 1: сразу снапшот
                if ("rooms" in element && "devices" in element) {
                    return rawData
                }

                // Вариант 2: поле text
                val textNode = element["text"]
                if (textNode is JsonPrimitive && textNode.isString) {
                    return textNode.content
                }

                // Вариант 3: message.text
                val messageNode = element["message"]
                if (messageNode is JsonObject) {
                    val innerText = messageNode["text"]
                    if (innerText is JsonPrimitive && innerText.isString) {
                        return innerText.content
                    }
                }
            }

            null
        } catch (e: Throwable) {
            Log.e("SocketsDevicesApi", "extractSnapshotJson parse error", e)
            null
        }
    }

}
