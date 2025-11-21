package com.tsd_store.deltahome.common.domain

import com.tsd_store.deltahome.common.network.ErrorApi
import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.network.ResultNetwork



fun <D, E : ErrorApi, R> ResultNetwork<D, E>.toResultDomain(
    mapSuccess: (D) -> R,
    mapError: (E) -> String
): ResultDomain<R> = when (this) {
    is ResultNetwork.Success -> ResultDomain.Success(mapSuccess(data))
    is ResultNetwork.Error -> ResultDomain.Error(mapError(error))
}