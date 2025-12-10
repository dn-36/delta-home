package com.tsd_store.deltahome.domain.actual_domain.usecases

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.CreateSensorRepositoryApi

class CreateSensorUseCase(

    private val repositoryApi: CreateSensorRepositoryApi

) {

    suspend fun execute(

        ui: String,

        value: List<String>

    ): ResultDomain<String> {

        return repositoryApi.createSensor(ui, value)

    }

}