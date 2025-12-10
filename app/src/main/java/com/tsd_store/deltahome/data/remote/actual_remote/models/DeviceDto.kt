package com.tsd_store.deltahome.data.remote.actual_remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class DeviceDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("ui") val ui: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("type_id") val typeId: Int,
    @SerialName("token") val token: String,
    @SerialName("status") val status: String,
    @SerialName("alarm") val alarm: String,
    @SerialName("type") val type: DeviceTypeDto
)

@kotlinx.serialization.Serializable
data class DeviceTypeDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("system_name") val systemName: String? = null,
    @SerialName("fields") val fields: List<DeviceFieldDto>,
    @SerialName("actions") val actions: List<DeviceActionDto> = emptyList()
)

@kotlinx.serialization.Serializable
data class DeviceFieldDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("device_type_id") val deviceTypeId: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("unit_id") val unitId: Int? = null,
    @SerialName("unit") val unit: UnitDto? = null
)

// сейчас actions пустые, но тип всё равно делаем
@kotlinx.serialization.Serializable
data class DeviceActionDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null
)

@Serializable
data class UnitDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("ui") val ui: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

typealias DevicesResponse = List<DeviceDto>