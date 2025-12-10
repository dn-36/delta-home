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
    val value: List<FieldValueDomain>,
    val name: String,
    val type: String,
    val unit: String?
)

@Serializable
data class FieldValueDomain(
    val value: String,
    val date: String
)