package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.data.remote.sensors_client.models.DeviceActionDto
import com.tsd_store.deltahome.domain.models.DeviceAction
 fun DeviceActionDto.toDomain(): DeviceAction =
    DeviceAction(
        id = id ,
        name = name.orEmpty(),
        command = name.orEmpty(),
        fieldId = null
    )
