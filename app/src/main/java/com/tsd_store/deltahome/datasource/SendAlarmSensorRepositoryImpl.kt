package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.data.remote.actual_remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.datasource.helper.converterStringResultDataToStringResultDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendAlarmSensorRepositoryApi

class SendAlarmSensorRepositoryImpl(

    private val sensorsClient: SensorsClient

): SendAlarmSensorRepositoryApi {

    override suspend fun sendAlarmSensor(
        ui: String,
        token: String,
        alarm: Int
    ): ResultDomain<String> {

        val result = sensorsClient.sendAlarmSensor(ui, token, alarm)

        return converterStringResultDataToStringResultDomain(result)

    }

}