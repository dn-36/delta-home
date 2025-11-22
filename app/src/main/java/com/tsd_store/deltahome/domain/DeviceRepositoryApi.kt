package com.tsd_store.deltahome.domain

import com.tsd_store.deltahome.domain.model.Device
import com.tsd_store.deltahome.domain.model.DeviceKind
import com.tsd_store.deltahome.domain.model.KettleDevice
import com.tsd_store.deltahome.domain.model.LampDevice
import com.tsd_store.deltahome.domain.model.LockDevice
import com.tsd_store.deltahome.domain.model.Room
import com.tsd_store.deltahome.domain.model.SensorDevice
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

    suspend fun addDevice(
        roomId: String,
        kind: DeviceKind,
        name: String
    ): Device

    /**
     * Подписка на изменения от сервера.
     * Когда приходит новый snapshot, репозиторий сам обновляет локальный кеш
     * и вызывает onUpdate(rooms, devices).
     */
    suspend fun subscribeDevicesSnapshots(
        coroutineScope: CoroutineScope,
        onUpdate: (rooms: List<Room>, devices: List<Device>) -> Unit
    )
}