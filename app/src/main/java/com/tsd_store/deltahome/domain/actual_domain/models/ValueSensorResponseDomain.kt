package com.tsd_store.deltahome.domain.actual_domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValueSensorResponseDomain(
    val ui: String,
    val name: String,
    @SerialName("fileds")
    val fields: List<FieldDtoDomain>
)

@Serializable
data class FieldDtoDomain(
    val value: List<String>,   // в JSON массив, при type="string" логично List<String>
    val name: String,          // "Ширина", "Долгота"
    val type: String,          // "string"
    val unit: String?          // null -> делаем nullable
)
