package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.data.remote.actual_remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.datasource.helper.converterStringResultDataToStringResultDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendStatusSensorRepositoryApi

class SendStatusSensorRepositoryImpl(

    private val sensorsClient: SensorsClient

): SendStatusSensorRepositoryApi {

    override suspend fun sendStatusSensor(
        ui: String,
        token: String,
        status: Int
    ): ResultDomain<String> {

        val result = sensorsClient.sendStatusSensor(ui, token, status)

        return converterStringResultDataToStringResultDomain(result)

    }

}