package com.tsd_store.deltahome.common.network


import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import kotlin.time.Duration
import kotlin.time.measureTime

suspend inline fun <reified T> makeRequest(

    url: String,
    method: HttpMethod,
    body: Any?,
    client: HttpClient,
    params: Map<String, Any>? = mapOf(),
    tag: String = "",
    contentType: ContentType = ContentType.Application.Json

): ResultNetwork<T, NetworkError> {

    val time: Duration

    val response: ResultNetwork<T, NetworkError>

    try {

        var result: HttpResponse

        time = measureTime {

            result = client.request(url) {

                this.method = method
                contentType(contentType)
                body?.let { setBody(it) }
                params?.forEach { (key, value) ->
                    parameter(key, value)
                }
            }

            Log.d("Description response", "${tag} - ${result.status} - ${result.bodyAsText()}")

            response = when (result.status.value) {

                in 200..299 -> ResultNetwork.Success(result.body() as T)
                401 -> ResultNetwork.Error(NetworkError.UNAUTHORIZED)
                in 500..599 -> ResultNetwork.Error(NetworkError.SERVER_ERROR)
                else -> ResultNetwork.Error(NetworkError.UNKNOWN)
            }
        }

        return response

    } catch (e: UnresolvedAddressException) {
        Log.d("NETWORK_RESPONCE", url + ": " + NetworkError.NO_INTERNET.name)
        return ResultNetwork.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        Log.d("NETWORK_RESPONCE", url + ": " + NetworkError.SERIALIZATION.name)
        return ResultNetwork.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        Log.d("NETWORK_RESPONCE", url + ": " + e.stackTraceToString())
        return ResultNetwork.Error(NetworkError.UNKNOWN)
    }
}

