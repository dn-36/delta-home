package com.tsd_store.deltahome.domain.actual_domain.usecases

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendStatusSensorRepositoryApi

class SendStatusSensorUseCase(

    private val repositoryApi: SendStatusSensorRepositoryApi

) {

    suspend fun execute(

        ui: String,

        token: String,

        status: Int

    ): ResultDomain<String> {

        return repositoryApi.sendStatusSensor(ui, token, status)

    }

}