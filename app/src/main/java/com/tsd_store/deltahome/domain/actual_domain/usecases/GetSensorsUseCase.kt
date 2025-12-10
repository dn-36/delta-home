package com.tsd_store.deltahome.domain.actual_domain.usecases

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.actual_domain.models.DevicesResponseDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.GetSensorsRepositoryApi

class GetSensorsUseCase(

    private val repositoryApi: GetSensorsRepositoryApi

) {

    suspend fun execute(): ResultDomain<DevicesResponseDomain> {

        return repositoryApi.getSensors()

    }

}