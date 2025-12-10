package com.tsd_store.deltahome.domain.actual_domain.repositories

import com.tsd_store.deltahome.common.domain.models.ResultDomain

interface SendAlarmSensorRepositoryApi {

    suspend fun sendAlarmSensor(

        ui: String,

        token: String,

        alarm: Int

    ): ResultDomain<String>

}