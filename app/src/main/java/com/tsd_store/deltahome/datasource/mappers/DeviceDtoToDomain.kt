package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.data.remote.sensors_client.models.DeviceDto
import com.tsd_store.deltahome.domain.models.Device

fun DeviceDto.toDomain(): Device =
    Device(
        id = id,
        name = name,
        ui = ui,
        token = token,
        status = status,
        alarm = alarm,
        type = type.toDomain()
    )