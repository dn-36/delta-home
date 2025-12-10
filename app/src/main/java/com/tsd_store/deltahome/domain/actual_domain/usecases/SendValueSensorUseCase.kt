package com.tsd_store.deltahome.domain.actual_domain.usecases

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendValueSensorRepositoryApi

class SendValueSensorUseCase(

    private val repositoryApi: SendValueSensorRepositoryApi

) {

    suspend fun execute(

        ui: String,

        token: String,

        field_id: String,

        value: String

    ): ResultDomain<String> {

        return repositoryApi.sendValueSensor(ui, token, field_id, value)

    }

}