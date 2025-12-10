package com.tsd_store.deltahome.datasource.helper

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.common.network.NetworkError

fun converterStringResultDataToStringResultDomain(

    response: ResultNetwork<String, NetworkError>

): ResultDomain<String> {

    return when(response) {

        is ResultNetwork.Success -> { ResultDomain.Success(response.data) }

        is ResultNetwork.Error -> { ResultDomain.Error(response.error.name) }

    }

}