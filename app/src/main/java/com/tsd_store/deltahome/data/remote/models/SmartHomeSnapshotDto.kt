package com.tsd_store.deltahome.data.remote.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy

@Serializable
data class SmartHomeSnapshotDto(
    val rooms: List<RoomDto> = emptyList(),
    val devices: List<DeviceDto> = emptyList()
)

