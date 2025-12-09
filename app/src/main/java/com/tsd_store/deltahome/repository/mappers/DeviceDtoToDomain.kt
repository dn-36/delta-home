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
import com.tsd_store.deltahome.domain.old_domain.model.SensorType


fun DeviceDto.toDomain(): Device = when (this) {

    is SensorDeviceDto -> SensorDevice(
        token = token,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        type = SensorType.valueOf(sensorType),
        value = value,
        isAlarm = isAlarm
    )

    is LampDeviceDto -> LampDevice(
        token = token,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isOn = isOn,
        brightness = brightness
    )

    is KettleDeviceDto -> KettleDevice(
        token = token,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isOn = isOn,
        targetTemperature = targetTemperature
    )

    is LockDeviceDto -> LockDevice(
        token = token,
        name = name,
        roomId = roomId,
        isFavorite = isFavorite,
        isLocked = isLocked
    )
}