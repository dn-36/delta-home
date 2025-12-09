package com.tsd_store.deltahome.repository.mappers

import com.tsd_store.deltahome.data.remote.old_remote.models.DeviceDto
import com.tsd_store.deltahome.data.remote.old_remote.models.KettleDeviceDto
import com.tsd_store.deltahome.data.remote.old_remote.models.LampDeviceDto
import com.tsd_store.deltahome.data.remote.old_remote.models.LockDeviceDto
import com.tsd_store.deltahome.data.remote.old_remote.models.SensorDeviceDto
import com.tsd_store.deltahome.domain.old_domain.model.Device
import com.tsd_store.deltahome.domain.old_domain.model.KettleDevice
import com.tsd_store.deltahome.domain.old_domain.model.LampDevice
import com.tsd_store.deltahome.domain.old_domain.model.LockDevice
import com.tsd_store.deltahome.domain.old_domain.model.SensorDevice


fun Device.toDto(): DeviceDto = when (this) {

    is SensorDevice -> SensorDeviceDto(
        token = token,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        sensorType = type.name,
        value = value,
        isAlarm = isAlarm
    )

    is LampDevice -> LampDeviceDto(
        token = token,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isOn = isOn,
        brightness = brightness
    )

    is KettleDevice -> KettleDeviceDto(
        token = token,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isOn = isOn,
        targetTemperature = targetTemperature
    )

    is LockDevice -> LockDeviceDto(
        token = token,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isLocked = isLocked
    )
}