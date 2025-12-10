package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.data.remote.actual_remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.datasource.helper.converterStringResultDataToStringResultDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.CreateSensorRepositoryApi

class CreateSensorRepositoryImpl(

    private val sensorsClient: SensorsClient

): CreateSensorRepositoryApi {

    override suspend fun createSensor(ui: String, value: List<String>): ResultDomain<String> {

        val result = sensorsClient.createSensor(ui, value)

        return converterStringResultDataToStringResultDomain(result)

    }

}