package com.tsd_store.deltahome.di

import com.tsd_store.deltahome.domain.DeviceRepositoryApi
import com.tsd_store.deltahome.domain.model.Device
import com.tsd_store.deltahome.domain.model.DeviceKind
import com.tsd_store.deltahome.domain.model.KettleDevice
import com.tsd_store.deltahome.domain.model.LampDevice
import com.tsd_store.deltahome.domain.model.LockDevice
import com.tsd_store.deltahome.domain.model.Room
import com.tsd_store.deltahome.domain.model.SensorDevice
import com.tsd_store.deltahome.domain.model.SensorType
import com.tsd_store.deltahome.feature.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID

val appModule = module {


    single<DeviceRepositoryApi> {
        FakeDeviceRepositoryImpl() as DeviceRepositoryApi
    }

        viewModel {
            HomeViewModel(
                repository = get()
            )
        }


}

class FakeDeviceRepositoryImpl : DeviceRepositoryApi {

    private val rooms = mutableListOf(
        Room(id = "living_room", name = "Living room"),
        Room(id = "kitchen",     name = "Kitchen"),
        Room(id = "hall",        name = "Hall")
    )

    private val devices = mutableListOf<Device>(
        SensorDevice(
            id = "sensor_temp_1",
            name = "Mi Temperature and humidity",
            roomId = "living_room",
            isFavorite = true,
            type = SensorType.TEMPERATURE,
            value = "26°C / 60%",
            isAlarm = false
        ),
        LampDevice(
            id = "lamp_1",
            name = "Lamp",
            roomId = "living_room",
            isFavorite = true,
            isOn = true,
            brightness = 70
        ),
        KettleDevice(
            id = "kettle_1",
            name = "Kettle",
            roomId = "kitchen",
            isFavorite = false,
            isOn = false,
            targetTemperature = 90
        ),
        LockDevice(
            id = "lock_1",
            name = "Smart Door Lock",
            roomId = "hall",
            isFavorite = true,
            isLocked = true
        )
    )

    // --- Rooms ---

    override suspend fun getRooms(): List<Room> = rooms.toList()

    override suspend fun addRoom(name: String): Room {
        val room = Room(
            id = UUID.randomUUID().toString(),
            name = name
        )
        rooms += room
        return room
    }

    override suspend fun deleteRoom(roomId: String) {
        rooms.removeAll { it.id == roomId }
        devices.removeAll { it.roomId == roomId }
    }

    override suspend fun addDevice(
        roomId: String,
        kind: DeviceKind,
        name: String
    ): Device {
        val id = UUID.randomUUID().toString()
        val device: Device = when (kind) {
            DeviceKind.SENSOR_TEMPERATURE -> SensorDevice(
                id = id,
                name = name.ifBlank { "Temperature sensor" },
                roomId = roomId,
                isFavorite = false,
                type = SensorType.TEMPERATURE,
                value = "25°C / 50%",
                isAlarm = false
            )

            DeviceKind.LAMP -> LampDevice(
                id = id,
                name = name.ifBlank { "Lamp" },
                roomId = roomId,
                isFavorite = false,
                isOn = false,
                brightness = 60
            )

            DeviceKind.KETTLE -> KettleDevice(
                id = id,
                name = name.ifBlank { "Kettle" },
                roomId = roomId,
                isFavorite = false,
                isOn = false,
                targetTemperature = 90
            )

            DeviceKind.LOCK -> LockDevice(
                id = id,
                name = name.ifBlank { "Smart lock" },
                roomId = roomId,
                isFavorite = false,
                isLocked = true
            )
        }
        devices += device
        return device
    }

    override suspend fun getDevices(): List<Device> =
        devices.toList()

    override suspend fun refreshSensors(): List<SensorDevice> =
        devices.filterIsInstance<SensorDevice>()

    override suspend fun setLampPower(lampId: String, isOn: Boolean): LampDevice {
        val index = devices.indexOfFirst { it.id == lampId && it is LampDevice }
        require(index >= 0) { "Lamp not found" }
        val old = devices[index] as LampDevice
        val updated = old.copy(isOn = isOn)
        devices[index] = updated
        return updated
    }

    override suspend fun setLampBrightness(lampId: String, brightness: Int): LampDevice {
        val index = devices.indexOfFirst { it.id == lampId && it is LampDevice }
        require(index >= 0) { "Lamp not found" }
        val old = devices[index] as LampDevice
        val updated = old.copy(brightness = brightness.coerceIn(0, 100))
        devices[index] = updated
        return updated
    }

    override suspend fun setKettlePower(kettleId: String, isOn: Boolean): KettleDevice {
        val index = devices.indexOfFirst { it.id == kettleId && it is KettleDevice }
        require(index >= 0) { "Kettle not found" }
        val old = devices[index] as KettleDevice
        val updated = old.copy(isOn = isOn)
        devices[index] = updated
        return updated
    }

    override suspend fun setKettleTemperature(
        kettleId: String,
        temperature: Int
    ): KettleDevice {
        val index = devices.indexOfFirst { it.id == kettleId && it is KettleDevice }
        require(index >= 0) { "Kettle not found" }
        val old = devices[index] as KettleDevice
        val updated = old.copy(
            targetTemperature = temperature.coerceIn(40, 100)
        )
        devices[index] = updated
        return updated
    }

    override suspend fun setLockState(lockId: String, isLocked: Boolean): LockDevice {
        val index = devices.indexOfFirst { it.id == lockId && it is LockDevice }
        require(index >= 0) { "Lock not found" }
        val old = devices[index] as LockDevice
        val updated = old.copy(isLocked = isLocked)
        devices[index] = updated
        return updated
    }
}