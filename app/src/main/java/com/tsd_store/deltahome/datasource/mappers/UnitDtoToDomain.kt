package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.data.remote.sensors_client.models.UnitDto
import com.tsd_store.deltahome.domain.models.DeviceUnit

fun UnitDto?.toDomain(): DeviceUnit? {
    if(this == null) return null
   return DeviceUnit(
        id = id,
        name = name,
        ui = ui
    )
}