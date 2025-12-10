package com.tsd_store.deltahome.domain.actual_domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class DeviceDtoDomain(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("ui") val ui: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("type_id") val typeId: Int,
    @SerialName("token") val token: String,
    @SerialName("status") val status: String,
    @SerialName("alarm") val alarm: String,
    @SerialName("type") val type: DeviceTypeDtoDomain
)

@kotlinx.serialization.Serializable
data class DeviceTypeDtoDomain(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("system_name") val systemName: String? = null,
    @SerialName("fields") val fields: List<DeviceFieldDtoDomain>,
    @SerialName("actions") val actions: List<DeviceActionDtoDomain> = emptyList()
)

@kotlinx.serialization.Serializable
data class DeviceFieldDtoDomain(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("device_type_id") val deviceTypeId: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("unit_id") val unitId: Int? = null,
    @SerialName("unit") val unit: UnitDtoDomain? = null
)

// сейчас actions пустые, но тип всё равно делаем
@kotlinx.serialization.Serializable
data class DeviceActionDtoDomain(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null
)

@Serializable
data class UnitDtoDomain(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("ui") val ui: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

typealias DevicesResponseDomain = List<DeviceDtoDomain>