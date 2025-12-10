package com.tsd_store.deltahome.domain.actual_domain.repositories

import com.tsd_store.deltahome.common.domain.models.ResultDomain

interface SendStatusSensorRepositoryApi {

    suspend fun sendStatusSensor(

        ui: String,

        token: String,

        status: Int

    ): ResultDomain<String>

}