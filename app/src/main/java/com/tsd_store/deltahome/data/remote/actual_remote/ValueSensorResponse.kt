package com.tsd_store.deltahome.data.remote.actual_remote

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
    val value: List<String>,   // в JSON массив, при type="string" логично List<String>
    val name: String,          // "Ширина", "Долгота"
    val type: String,          // "string"
    val unit: String?          // null -> делаем nullable
)
