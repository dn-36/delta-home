package com.tsd_store.deltahome.repository

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.domain.toResultDomain
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.common.network.asEmptyDataResult
import com.tsd_store.deltahome.common.network.map
import com.tsd_store.deltahome.common.network.onError
import com.tsd_store.deltahome.data.remote.models.DeviceDto
import com.tsd_store.deltahome.data.remote.models.RoomDto
import com.tsd_store.deltahome.datasource.SmartHomeJsonDataSource
import com.tsd_store.deltahome.datasource.mappers.toDomain
import com.tsd_store.deltahome.domain.DeviceRepositoryApi
import com.tsd_store.deltahome.domain.model.Device
import com.tsd_store.deltahome.domain.model.DeviceKind
import com.tsd_store.deltahome.domain.model.KettleDevice
import com.tsd_store.deltahome.domain.model.LampDevice
import com.tsd_store.deltahome.domain.model.LockDevice
import com.tsd_store.deltahome.domain.model.Room
import com.tsd_store.deltahome.domain.model.SensorDevice
import com.tsd_store.deltahome.domain.model.SensorType
import java.util.UUID


class DeviceRepositoryImpl(
    private val dataSource: SmartHomeJsonDataSource
) : DeviceRepositoryApi {

    // --- Rooms ---

    override suspend fun getRooms(): List<Room> {
        val result = dataSource.loadSnapshot()
            .map { snapshot -> snapshot.rooms.map { it.toDomain() } }

        return when (result.toResultDomain(
            mapSuccess = { it },
            mapError = { error -> "Failed to load rooms: $error" }
        )) {
            is ResultDomain.Success -> {
                (result as ResultNetwork.Success).data
            }
            is ResultDomain.Error -> emptyList() // можно кинуть исключение, если хочешь
        }
    }

    override suspend fun addRoom(name: String): Room {
        val result = dataSource.updateSnapshot { snapshot ->
            val newRoom = RoomDto(
                id = UUID.randomUUID().toString(),
                name = name
            )
            snapshot.copy(rooms = snapshot.rooms + newRoom)
        }

        val domainResult = result.toResultDomain(
            mapSuccess = { dto ->
                dto.rooms.last().toDomain()
            },
            mapError = { "Failed to add room: $it" }
        )

        return when (domainResult) {
            is ResultDomain.Success -> domainResult.data
            is ResultDomain.Error -> error(domainResult.error)
        }
    }

    override suspend fun deleteRoom(roomId: String) {
        val result = dataSource.updateSnapshot { snapshot ->
            snapshot.copy(
                rooms = snapshot.rooms.filterNot { it.id == roomId },
                devices = snapshot.devices.filterNot { it.roomId == roomId }
            )
        }

        // Нам не нужен data, превращаем в EmptyResult и просто игнорируем
        result
            .asEmptyDataResult()
            .onError { /* тут можно залогировать */ }
    }

    // --- Devices ---

    override suspend fun getDevices(): List<Device> {
        val result = dataSource.loadSnapshot()
            .map { snapshot -> snapshot.devices.map { it.toDomain() } }

        return when (val domain = result.toResultDomain(
            mapSuccess = { it },
            mapError = { "Failed to load devices: $it" }
        )) {
            is ResultDomain.Success -> (result as ResultNetwork.Success).data
            is ResultDomain.Error -> emptyList()
        }
    }

    override suspend fun refreshSensors(): List<SensorDevice> {
        val result = dataSource.loadSnapshot()
            .map { snapshot ->
                snapshot.devices
                    .map { it.toDomain() }
                    .filterIsInstance<SensorDevice>()
            }

        return when (val domain = result.toResultDomain(
            mapSuccess = { it },
            mapError = { "Failed to refresh sensors: $it" }
        )) {
            is ResultDomain.Success -> (result as ResultNetwork.Success).data
            is ResultDomain.Error -> emptyList()
        }
    }

    override suspend fun setLampPower(lampId: String, isOn: Boolean): LampDevice {
        val result = dataSource.updateSnapshot { snapshot ->
            val updatedDevices = snapshot.devices.map { dto ->
                if (dto.id == lampId && dto.type == "lamp") {
                    dto.copy(isOn = isOn)
                } else dto
            }
            snapshot.copy(devices = updatedDevices)
        }

        val domain = result.toResultDomain(
            mapSuccess = { dto ->
                dto.devices.first { it.id == lampId && it.type == "lamp" }
                    .toDomain() as LampDevice
            },
            mapError = { "Failed to set lamp power: $it" }
        )

        return when (domain) {
            is ResultDomain.Success -> domain.data
            is ResultDomain.Error -> error(domain.error)
        }
    }

    override suspend fun setLampBrightness(lampId: String, brightness: Int): LampDevice {
        val clamped = brightness.coerceIn(0, 100)
        val result = dataSource.updateSnapshot { snapshot ->
            val updatedDevices = snapshot.devices.map { dto ->
                if (dto.id == lampId && dto.type == "lamp") {
                    dto.copy(brightness = clamped)
                } else dto
            }
            snapshot.copy(devices = updatedDevices)
        }

        val domain = result.toResultDomain(
            mapSuccess = { dto ->
                dto.devices.first { it.id == lampId && it.type == "lamp" }
                    .toDomain() as LampDevice
            },
            mapError = { "Failed to set lamp brightness: $it" }
        )

        return when (domain) {
            is ResultDomain.Success -> domain.data
            is ResultDomain.Error -> error(domain.error)
        }
    }

    override suspend fun setKettlePower(kettleId: String, isOn: Boolean): KettleDevice {
        val result = dataSource.updateSnapshot { snapshot ->
            val updatedDevices = snapshot.devices.map { dto ->
                if (dto.id == kettleId && dto.type == "kettle") {
                    dto.copy(isOn = isOn)
                } else dto
            }
            snapshot.copy(devices = updatedDevices)
        }

        val domain = result.toResultDomain(
            mapSuccess = { dto ->
                dto.devices.first { it.id == kettleId && it.type == "kettle" }
                    .toDomain() as KettleDevice
            },
            mapError = { "Failed to set kettle power: $it" }
        )

        return when (domain) {
            is ResultDomain.Success -> domain.data
            is ResultDomain.Error -> error(domain.error)
        }
    }

    override suspend fun setKettleTemperature(
        kettleId: String,
        temperature: Int
    ): KettleDevice {
        val clamped = temperature.coerceIn(40, 100)
        val result = dataSource.updateSnapshot { snapshot ->
            val updatedDevices = snapshot.devices.map { dto ->
                if (dto.id == kettleId && dto.type == "kettle") {
                    dto.copy(targetTemperature = clamped)
                } else dto
            }
            snapshot.copy(devices = updatedDevices)
        }

        val domain = result.toResultDomain(
            mapSuccess = { dto ->
                dto.devices.first { it.id == kettleId && it.type == "kettle" }
                    .toDomain() as KettleDevice
            },
            mapError = { "Failed to set kettle temperature: $it" }
        )

        return when (domain) {
            is ResultDomain.Success -> domain.data
            is ResultDomain.Error -> error(domain.error)
        }
    }

    override suspend fun setLockState(lockId: String, isLocked: Boolean): LockDevice {
        val result = dataSource.updateSnapshot { snapshot ->
            val updatedDevices = snapshot.devices.map { dto ->
                if (dto.id == lockId && dto.type == "lock") {
                    dto.copy(isLocked = isLocked)
                } else dto
            }
            snapshot.copy(devices = updatedDevices)
        }

        val domain = result.toResultDomain(
            mapSuccess = { dto ->
                dto.devices.first { it.id == lockId && it.type == "lock" }
                    .toDomain() as LockDevice
            },
            mapError = { "Failed to set lock state: $it" }
        )

        return when (domain) {
            is ResultDomain.Success -> domain.data
            is ResultDomain.Error -> error(domain.error)
        }
    }

    // Добавление техники (используется HomeAction.AddDevice)

    override suspend fun addDevice(
        roomId: String,
        kind: DeviceKind,
        name: String
    ): Device {
        val result = dataSource.updateSnapshot { snapshot ->
            val id = UUID.randomUUID().toString()
            val baseName = name.ifBlank {
                when (kind) {
                    DeviceKind.LAMP -> "Lamp"
                    DeviceKind.KETTLE -> "Kettle"
                    DeviceKind.LOCK -> "Smart lock"
                    DeviceKind.SENSOR_TEMPERATURE -> "Temperature sensor"
                }
            }

            val newDeviceDto = when (kind) {
                DeviceKind.SENSOR_TEMPERATURE -> DeviceDto(
                    id = id,
                    name = baseName,
                    roomId = roomId,
                    isFavorite = false,
                    type = "sensor",
                    sensorType = SensorType.TEMPERATURE.name,
                    value = "25°C / 50%",
                    isAlarm = false
                )
                DeviceKind.LAMP -> DeviceDto(
                    id = id,
                    name = baseName,
                    roomId = roomId,
                    isFavorite = false,
                    type = "lamp",
                    isOn = false,
                    brightness = 60
                )
                DeviceKind.KETTLE -> DeviceDto(
                    id = id,
                    name = baseName,
                    roomId = roomId,
                    isFavorite = false,
                    type = "kettle",
                    isOn = false,
                    targetTemperature = 90
                )
                DeviceKind.LOCK -> DeviceDto(
                    id = id,
                    name = baseName,
                    roomId = roomId,
                    isFavorite = false,
                    type = "lock",
                    isLocked = true
                )
            }

            snapshot.copy(
                devices = snapshot.devices + newDeviceDto
            )
        }

        val domain = result.toResultDomain(
            mapSuccess = { dto ->
                dto.devices.last().toDomain()
            },
            mapError = { "Failed to add device: $it" }
        )

        return when (domain) {
            is ResultDomain.Success -> domain.data
            is ResultDomain.Error -> error(domain.error)
        }
    }
}