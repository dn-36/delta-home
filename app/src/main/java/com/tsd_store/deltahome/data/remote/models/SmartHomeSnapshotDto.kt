package com.tsd_store.deltahome.data.remote.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy

@Serializable
data class SmartHomeSnapshotDto(
    val rooms: List<RoomDto>,
    val devices: List<DeviceDto>
)