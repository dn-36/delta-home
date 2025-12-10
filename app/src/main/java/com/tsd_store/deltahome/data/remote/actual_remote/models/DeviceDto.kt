package com.tsd_store.deltahome.data.remote.actual_remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    val id: Int,
    val name: String,
    val ui: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("type_id") val typeId: Int,
    val token: String,
    val status: String,
    val alarm: String,
    val type: DeviceTypeDto
)

@Serializable
data class DeviceTypeDto(
    val id: Int,
    val name: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("system_name") val systemName: String? = null,
    val fields: List<DeviceFieldDto> = emptyList(),
    val actions: List<DeviceActionDto> = emptyList()
)

@Serializable
data class DeviceFieldDto(
    val id: Int,
    val name: String,
    val type: String,
    @SerialName("device_type_id") val deviceTypeId: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("unit_id") val unitId: Int? = null,
    val unit: UnitDto? = null
)

@Serializable
data class UnitDto(
    val id: Int,
    val name: String,
    val ui: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class DeviceActionDto(
    val id: Int,
    val name: String,
    val command: String,
    val ui: String,
    @SerialName("device_field_id") val deviceFieldId: Int,
    @SerialName("device_type_id") val deviceTypeId: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

typealias DevicesResponse = List<DeviceDto>

