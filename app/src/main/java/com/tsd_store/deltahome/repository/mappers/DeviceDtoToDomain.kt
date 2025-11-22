package com.tsd_store.deltahome.repository.mappers

import com.tsd_store.deltahome.data.remote.models.DeviceDto
import com.tsd_store.deltahome.domain.model.Device
import com.tsd_store.deltahome.domain.model.KettleDevice
import com.tsd_store.deltahome.domain.model.LampDevice
import com.tsd_store.deltahome.domain.model.LockDevice
import com.tsd_store.deltahome.domain.model.SensorDevice
import com.tsd_store.deltahome.domain.model.SensorType


fun DeviceDto.toDomain(): Device = when (type.lowercase()) {
    "sensor" -> SensorDevice(
        id = id,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        type = SensorType.valueOf(sensorType ?: SensorType.TEMPERATURE.name),
        value = value.orEmpty(),
        isAlarm = isAlarm ?: false
    )
    "lamp" -> LampDevice(
        id = id,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isOn = isOn ?: false,
        brightness = brightness ?: 0
    )
    "kettle" -> KettleDevice(
        id = id,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isOn = isOn ?: false,
        targetTemperature = targetTemperature ?: 90
    )
    "lock" -> LockDevice(
        id = id,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isLocked = isLocked ?: true
    )
    else -> error("Unknown device type: $type")
}