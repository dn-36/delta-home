package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.data.remote.models.DeviceDto
import com.tsd_store.deltahome.domain.model.Device
import com.tsd_store.deltahome.domain.model.KettleDevice
import com.tsd_store.deltahome.domain.model.LampDevice
import com.tsd_store.deltahome.domain.model.LockDevice
import com.tsd_store.deltahome.domain.model.SensorDevice


fun Device.toDto(): DeviceDto = when (this) {
    is SensorDevice -> DeviceDto(
        id = id,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        type = "sensor",
        sensorType = type.name,
        value = value,
        isAlarm = isAlarm
    )
    is LampDevice -> DeviceDto(
        id = id,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        type = "lamp",
        isOn = isOn,
        brightness = brightness
    )
    is KettleDevice -> DeviceDto(
        id = id,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        type = "kettle",
        isOn = isOn,
        targetTemperature = targetTemperature
    )
    is LockDevice -> DeviceDto(
        id = id,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        type = "lock",
        isLocked = isLocked
    )
}