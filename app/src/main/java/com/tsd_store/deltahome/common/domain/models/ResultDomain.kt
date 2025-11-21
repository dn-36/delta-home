package com.tsd_store.deltahome.common.domain.models

sealed interface ResultDomain<out D> {

    data class Success<out D>(val data: D): ResultDomain<D>

    data class Error(val error:String):

        ResultDomain<Nothing>

}