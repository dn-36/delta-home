package com.tsd_store.deltahome.domain.actual_domain.usecases

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.actual_domain.models.ValueSensorResponseDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.GetValueSensorRepositoryApi

class GetValueSensorUseCase(

    private val repositoryApi: GetValueSensorRepositoryApi

) {

    suspend fun execute(ui: String): ResultDomain<ValueSensorResponseDomain> {

        return repositoryApi.getValueSensor(ui)

    }

}