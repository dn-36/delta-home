package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.data.remote.sensors_client.models.DeviceFieldDto
import com.tsd_store.deltahome.domain.models.DeviceField

fun DeviceFieldDto.toDomain(): DeviceField =
    DeviceField(
        id = id,
        name = name,
        type = type.mapFieldType(),
        unit = unit.toDomain(),
        lastValue = null
    )