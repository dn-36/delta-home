package com.tsd_store.deltahome.data.remote.actual_remote.rooms_client.models

import kotlinx.serialization.Serializable

@Serializable
data class DeleteOrAddSensorFromRoomRequestModel(

    val token: String,

    val ui: String,

    val device_token: String,

    val device_ui: String

)
