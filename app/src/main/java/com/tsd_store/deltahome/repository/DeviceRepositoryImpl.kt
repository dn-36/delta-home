package com.tsd_store.deltahome.repository

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.network.NetworkError
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.data.remote.old_remote.SmartHomeRemoteDataSource
import com.tsd_store.deltahome.data.remote.old_remote.models.SmartHomeSnapshotDto
import com.tsd_store.deltahome.repository.mappers.toDomain
import com.tsd_store.deltahome.domain.old_domain.DeviceRepositoryApi
import com.tsd_store.deltahome.domain.old_domain.SmartHomeSyncApi
import com.tsd_store.deltahome.domain.old_domain.model.Device
import com.tsd_store.deltahome.domain.old_domain.model.DeviceKind
import com.tsd_store.deltahome.domain.old_domain.model.KettleDevice
import com.tsd_store.deltahome.domain.old_domain.model.LampDevice
import com.tsd_store.deltahome.domain.old_domain.model.LockDevice
import com.tsd_store.deltahome.domain.old_domain.model.Room
import com.tsd_store.deltahome.domain.old_domain.model.SensorDevice
import com.tsd_store.deltahome.domain.old_domain.model.SensorType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID


class DeviceRepositoryImpl(
    private val remote: SmartHomeRemoteDataSource,
    private val syncApi: SmartHomeSyncApi
) : DeviceRepositoryApi {

    // --- локальный кеш состояния ---
    private var roomsCache: List<Room> = emptyList()
    private var devicesCache: List<Device> = emptyList()

    private val mutex = Mutex()


    private suspend fun ensureLoaded() {
        if (roomsCache.isNotEmpty() || devicesCache.isNotEmpty()) return

        when (val result: ResultNetwork<SmartHomeSnapshotDto, NetworkError> =
            remote.getSmartHomeSnapshotDto()
        ) {
            is ResultNetwork.Success -> applySnapshot(result.data)
            is ResultNetwork.Error -> {
                // на примере просто игнорируем и оставляем пустые списки
                // можно кинуть исключение или логировать
            }
        }
    }

    private suspend fun applySnapshot(dto: SmartHomeSnapshotDto) {
        mutex.withLock {
            roomsCache = dto.rooms.map { it.toDomain() }
            devicesCache = dto.devices.map { it.toDomain() }
        }
    }


    private suspend fun pushSnapshotToServer() {
        val rooms: List<Room>
        val devices: List<Device>

        mutex.withLock {
            rooms = roomsCache
            devices = devicesCache
        }

        // Ошибку пока не пробрасываем выше, только логируем
        when (val result = syncApi.syncState(rooms, devices)) {
            is ResultDomain.Success -> {
                println("SmartHomeSync: success")
            }
            is ResultDomain.Error -> {
                println("SmartHomeSync: error ${result.error}")
            }
        }
    }



    override suspend fun getRooms(): List<Room> {
        ensureLoaded()
        return roomsCache
    }

    override suspend fun addRoom(name: String): Room {
        ensureLoaded()

        val room = Room(
            id = UUID.randomUUID().toString(),
            name = name
        )

        mutex.withLock {
            roomsCache = roomsCache + room
        }

        pushSnapshotToServer()

        return room
    }

    override suspend fun deleteRoom(roomId: String) {
        ensureLoaded()

        mutex.withLock {
            roomsCache = roomsCache.filterNot { it.id == roomId }
            devicesCache = devicesCache.filterNot { it.roomId == roomId }
        }

        pushSnapshotToServer()
    }

    override suspend fun getDevices(): List<Device> {
        ensureLoaded()
        return devicesCache
    }

    override suspend fun refreshSensors(): List<SensorDevice> {
        ensureLoaded()
        return devicesCache.filterIsInstance<SensorDevice>()
    }

    override suspend fun setLampPower(lampId: String, isOn: Boolean): LampDevice {
        ensureLoaded()
        var updated: LampDevice? = null

        mutex.withLock {
            devicesCache = devicesCache.map { device ->
                if (device is LampDevice && device.token == lampId) {
                    val new = device.copy(isOn = isOn)
                    updated = new
                    new
                } else device
            }
        }

        pushSnapshotToServer()

        return updated ?: error("Lamp not found: $lampId")
    }

    override suspend fun setLampBrightness(
        lampId: String,
        brightness: Int
    ): LampDevice {
        ensureLoaded()
        val clamped = brightness.coerceIn(0, 100)
        var updated: LampDevice? = null

        mutex.withLock {
            devicesCache = devicesCache.map { device ->
                if (device is LampDevice && device.token == lampId) {
                    val new = device.copy(brightness = clamped)
                    updated = new
                    new
                } else device
            }
        }

        pushSnapshotToServer()

        return updated ?: error("Lamp not found: $lampId")
    }

    override suspend fun setKettlePower(
        kettleId: String,
        isOn: Boolean
    ): KettleDevice {
        ensureLoaded()
        var updated: KettleDevice? = null

        mutex.withLock {
            devicesCache = devicesCache.map { device ->
                if (device is KettleDevice && device.token == kettleId) {
                    val new = device.copy(isOn = isOn)
                    updated = new
                    new
                } else device
            }
        }

        pushSnapshotToServer()

        return updated ?: error("Kettle not found: $kettleId")
    }

    override suspend fun setKettleTemperature(
        kettleId: String,
        temperature: Int
    ): KettleDevice {
        ensureLoaded()
        val clamped = temperature.coerceIn(40, 100)
        var updated: KettleDevice? = null

        mutex.withLock {
            devicesCache = devicesCache.map { device ->
                if (device is KettleDevice && device.token == kettleId) {
                    val new = device.copy(targetTemperature = clamped)
                    updated = new
                    new
                } else device
            }
        }

        pushSnapshotToServer()

        return updated ?: error("Kettle not found: $kettleId")
    }

    override suspend fun setLockState(
        lockId: String,
        isLocked: Boolean
    ): LockDevice {
        ensureLoaded()
        var updated: LockDevice? = null

        mutex.withLock {
            devicesCache = devicesCache.map { device ->
                if (device is LockDevice && device.token == lockId) {
                    val new = device.copy(isLocked = isLocked)
                    updated = new
                    new
                } else device
            }
        }

        pushSnapshotToServer()

        return updated ?: error("Lock not found: $lockId")
    }



    override suspend fun addDevice(
        roomId: String,
        kind: DeviceKind,
        name: String
    ): Device {
        ensureLoaded()

        return when (kind) {

            DeviceKind.SENSOR_TEMPERATURE ->
                addSensor(roomId, SensorType.TEMPERATURE, name)

            DeviceKind.SENSOR_WATER_LEAK ->
                addSensor(roomId, SensorType.WATER_LEAK, name)

            DeviceKind.SENSOR_SMOKE ->
                addSensor(roomId, SensorType.SMOKE, name)

            DeviceKind.SENSOR_ELECTRICITY ->
                addSensor(roomId, SensorType.ELECTRICITY, name)

            DeviceKind.LAMP -> {
                val device = LampDevice(
                    token = UUID.randomUUID().toString(),
                    name = if (name.isBlank()) "Lamp" else name,
                    roomId = roomId,
                    isFavorite = false,
                    isOn = false,
                    brightness = 60
                )
                mutex.withLock {
                    devicesCache = devicesCache + device
                }
                pushSnapshotToServer()
                device
            }

            DeviceKind.KETTLE -> {
                val device = KettleDevice(
                    token = UUID.randomUUID().toString(),
                    name = if (name.isBlank()) "Kettle" else name,
                    roomId = roomId,
                    isFavorite = false,
                    isOn = false,
                    targetTemperature = 90
                )
                mutex.withLock {
                    devicesCache = devicesCache + device
                }
                pushSnapshotToServer()
                device
            }

            DeviceKind.LOCK -> {
                val device = LockDevice(
                    token = UUID.randomUUID().toString(),
                    name = if (name.isBlank()) "Smart lock" else name,
                    roomId = roomId,
                    isFavorite = false,
                    isLocked = true
                )
                mutex.withLock {
                    devicesCache = devicesCache + device
                }
                pushSnapshotToServer()
                device
            }
        }
    }

    override suspend fun addSensor(
        roomId: String,
        sensorType: SensorType,
        name: String
    ): SensorDevice {
        ensureLoaded()

        val device = SensorDevice(
            token = UUID.randomUUID().toString(),
            name =  name,
            roomId = roomId,
            isFavorite = false,
            type = sensorType,
            value = sensorType.name,
            isAlarm = false
        )

        mutex.withLock {
            devicesCache = devicesCache + device
        }

        pushSnapshotToServer()

        return device
    }


    override suspend fun subscribeDevicesSnapshots(
        coroutineScope: CoroutineScope,
        onUpdate: (List<Room>, List<Device>) -> Unit,
    ) {

        remote.subscribeChanelChangeDataStateDevices { snapshotDto ->
            val rooms = snapshotDto.rooms.map { it.toDomain() }
            val devices = snapshotDto.devices.map { it.toDomain() }

            runBlocking {
                mutex.withLock {
                    roomsCache = rooms
                    devicesCache = devices
                }
            }

            onUpdate(rooms, devices)
        }
    }
}