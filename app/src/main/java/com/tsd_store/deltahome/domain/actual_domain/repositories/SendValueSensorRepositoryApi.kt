package com.tsd_store.deltahome.domain.actual_domain.repositories

import com.tsd_store.deltahome.common.domain.models.ResultDomain

interface SendValueSensorRepositoryApi {

    suspend fun sendValueSensor(

        ui: String,

        token: String,

        field_id: String,

        value: String

    ): ResultDomain<String>

}