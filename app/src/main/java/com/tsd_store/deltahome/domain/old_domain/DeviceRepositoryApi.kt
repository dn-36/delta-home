package com.tsd_store.deltahome.domain.old_domain

import com.tsd_store.deltahome.domain.old_domain.model.Device
import com.tsd_store.deltahome.domain.old_domain.model.DeviceKind
import com.tsd_store.deltahome.domain.old_domain.model.KettleDevice
import com.tsd_store.deltahome.domain.old_domain.model.LampDevice
import com.tsd_store.deltahome.domain.old_domain.model.LockDevice
import com.tsd_store.deltahome.domain.old_domain.model.Room
import com.tsd_store.deltahome.domain.old_domain.model.SensorDevice
import com.tsd_store.deltahome.domain.old_domain.model.SensorType
import kotlinx.coroutines.CoroutineScope

interface DeviceRepositoryApi {

    // Rooms
    suspend fun getRooms(): List<Room>
    suspend fun addRoom(name: String): Room
    suspend fun deleteRoom(roomId: String)

    // Devices
    suspend fun getDevices(): List<Device>
    suspend fun refreshSensors(): List<SensorDevice>

    suspend fun setLampPower(lampId: String, isOn: Boolean): LampDevice
    suspend fun setLampBrightness(lampId: String, brightness: Int): LampDevice

    suspend fun setKettlePower(kettleId: String, isOn: Boolean): KettleDevice
    suspend fun setKettleTemperature(kettleId: String, temperature: Int): KettleDevice

    suspend fun setLockState(lockId: String, isLocked: Boolean): LockDevice

    suspend fun addDevice(roomId: String, kind: DeviceKind, name: String): Device

    // 🔥 новый удобный метод — добавить сенсор конкретного типа
    suspend fun addSensor(
        roomId: String,
        sensorType: SensorType,
        name: String
    ): SensorDevice


    suspend fun subscribeDevicesSnapshots(
        coroutineScope: CoroutineScope,
        onUpdate: (rooms: List<Room>, devices: List<Device>) -> Unit
    )
}