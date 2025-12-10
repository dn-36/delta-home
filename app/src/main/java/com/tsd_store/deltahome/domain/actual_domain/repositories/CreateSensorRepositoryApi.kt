package com.tsd_store.deltahome.domain.actual_domain.repositories

import com.tsd_store.deltahome.common.domain.models.ResultDomain

interface CreateSensorRepositoryApi {

    suspend fun createSensor(

        ui: String,

        value: List<String>

    ): ResultDomain<String>

}