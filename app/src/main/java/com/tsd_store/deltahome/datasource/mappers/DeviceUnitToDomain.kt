package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.domain.models.DeviceUnit

private fun DeviceUnit.toDomain(): DeviceUnit =
    DeviceUnit(
        id = id,
        name = name,
        ui = ui
    )