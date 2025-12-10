package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.data.remote.actual_remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.datasource.helper.converterStringResultDataToStringResultDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendValueSensorRepositoryApi

class SendValueSensorRepositoryImpl(

    private val sensorsClient: SensorsClient

): SendValueSensorRepositoryApi {

    override suspend fun sendValueSensor(
        ui: String,
        token: String,
        field_id: String,
        value: String
    ): ResultDomain<String> {

        val result = sensorsClient.sendValueSensor(ui, token, field_id, value)

        return converterStringResultDataToStringResultDomain(result)

    }

}