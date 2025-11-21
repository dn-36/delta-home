package com.tsd_store.deltahome.data.remote

import com.tsd_store.deltahome.common.network.EmptyResult
import com.tsd_store.deltahome.common.network.NetworkError
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.data.remote.models.DeviceDto
import com.tsd_store.deltahome.data.remote.models.RoomDto
import com.tsd_store.deltahome.data.remote.models.SmartHomeSnapshotDto
import com.tsd_store.deltahome.domain.model.SensorType
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class SmartHomeJsonFakeBackend(
    private val json: Json = defaultJson
) {

    // JSON-строка, с которой мы "работаем как с бэкендом"
    private var snapshotJsonText: String = initialJson

    fun getSnapshotJson(): String = snapshotJsonText

    fun setSnapshotJson(newJson: String) {
        snapshotJsonText = newJson
    }

    fun parseSnapshot(): ResultNetwork<SmartHomeSnapshotDto, NetworkError> {
        return try {
            val dto = json.decodeFromString<SmartHomeSnapshotDto>(snapshotJsonText)
            ResultNetwork.Success(dto)
        } catch (e: kotlinx.serialization.SerializationException) {
            ResultNetwork.Error(NetworkError.SERIALIZATION)
        } catch (e: Throwable) {
            ResultNetwork.Error(NetworkError.UNKNOWN)
        }
    }

    fun saveSnapshot(dto: SmartHomeSnapshotDto): EmptyResult<NetworkError> {
        return try {
            snapshotJsonText = json.encodeToString(dto)  // ✅ теперь ок
            ResultNetwork.Success(Unit)
        } catch (e: kotlinx.serialization.SerializationException) {
            ResultNetwork.Error(NetworkError.SERIALIZATION)
        } catch (e: Throwable) {
            ResultNetwork.Error(NetworkError.UNKNOWN)
        }
    }

    companion object {
        val defaultJson = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

        // Стартовый JSON: по одной сущности каждого типа
        private val initialJson: String = defaultJson.encodeToString(
             SmartHomeSnapshotDto(
                rooms = listOf(
                    RoomDto(id = "living_room", name = "Living room"),
                    RoomDto(id = "kitchen", name = "Kitchen"),
                    RoomDto(id = "hall", name = "Hall")
                ),
                devices = listOf(
                    DeviceDto(
                        id = "sensor_temp_1",
                        name = "Mi Temperature and humidity",
                        roomId = "living_room",
                        isFavorite = true,
                        type = "sensor",
                        sensorType = SensorType.TEMPERATURE.name,
                        value = "26°C / 60%",
                        isAlarm = false
                    ),
                    DeviceDto(
                        id = "lamp_1",
                        name = "Lamp",
                        roomId = "living_room",
                        isFavorite = true,
                        type = "lamp",
                        isOn = true,
                        brightness = 70
                    ),
                    DeviceDto(
                        id = "kettle_1",
                        name = "Kettle",
                        roomId = "kitchen",
                        isFavorite = false,
                        type = "kettle",
                        isOn = false,
                        targetTemperature = 90
                    ),
                    DeviceDto(
                        id = "lock_1",
                        name = "Smart Door Lock",
                        roomId = "hall",
                        isFavorite = true,
                        type = "lock",
                        isLocked = true
                    )
                )
            )
        )
    }

}

