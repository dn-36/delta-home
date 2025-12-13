package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.data.remote.sensors_client.models.DeviceTypeDto
import com.tsd_store.deltahome.domain.models.DeviceType
fun DeviceTypeDto.toDomain(): DeviceType =
    DeviceType(
        id = id,
        name = name,
        systemName = systemName,
        category = (systemName?:"").mapCategory(),
        fields = fields.map { it.toDomain() },
        actions = actions.map { it.toDomain() }
    )