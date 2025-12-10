package com.tsd_store.deltahome.data.remote.actual_remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValueSensorResponse(
    val ui: String,
    val name: String,
    @SerialName("fileds")
    val fields: List<FieldDto>
)

@Serializable
data class FieldDto(
    val value: List<FieldValue>,
    val name: String,
    val type: String,
    val unit: String?
)

@Serializable
data class FieldValue(
    val value: String,
    val date: String
)
