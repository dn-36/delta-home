package com.tsd_store.deltahome.domain.actual_domain.usecases

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendAlarmSensorRepositoryApi

class SendAlarmSensorUseCase(

    private val repositoryApi: SendAlarmSensorRepositoryApi

) {

    suspend fun execute(

        ui: String,

        token: String,

        alarm: Int

    ): ResultDomain<String> {

        return repositoryApi.sendAlarmSensor(ui, token, alarm)

    }

}