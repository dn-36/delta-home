package com.tsd_store.deltahome.data.remote.actual_remote.rooms_client.models

import kotlinx.serialization.Serializable

@Serializable
data class DeleteRoomRequestModel(

    val token: String,

    val ui: String

)

