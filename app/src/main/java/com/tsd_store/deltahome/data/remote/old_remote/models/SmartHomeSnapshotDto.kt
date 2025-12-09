package com.tsd_store.deltahome.data.remote.old_remote.models

import kotlinx.serialization.Serializable

@Serializable
data class SmartHomeSnapshotDto(
    val rooms: List<RoomDto> = emptyList(),
    val devices: List<DeviceDto> = emptyList()
)

