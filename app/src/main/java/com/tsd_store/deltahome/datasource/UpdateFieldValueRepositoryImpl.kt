package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.domain.toResultDomain
import com.tsd_store.deltahome.data.remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.domain.models.DeviceField
import com.tsd_store.deltahome.domain.repositories.UpdateFieldValueRepository

class UpdateFieldValueRepositoryImpl(
    private val sensorsClient : SensorsClient
) : UpdateFieldValueRepository {

    override suspend fun updateFieldValue(
        ui: String,
        token: String,
        field: DeviceField,
        newRawValue: String,
    ): ResultDomain<Unit> {
        return sensorsClient.sendValueSensor(
            ui,token, field.id.toString(),newRawValue
        ).toResultDomain({
            Unit
        },
            {
                it.name
            }
        )
    }
}