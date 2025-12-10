package com.tsd_store.deltahome.domain.actual_domain.repositories

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.actual_domain.models.ValueSensorResponseDomain

interface GetValueSensorRepositoryApi {

    suspend fun getValueSensor(ui: String): ResultDomain<ValueSensorResponseDomain>

}