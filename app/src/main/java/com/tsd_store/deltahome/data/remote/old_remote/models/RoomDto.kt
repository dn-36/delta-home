package com.tsd_store.deltahome.data.remote.old_remote.models

import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(
    val id: String,
    val name: String
)