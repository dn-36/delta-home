package com.tsd_store.deltahome.data.remote.actual_remote.models

import kotlinx.serialization.Serializable


@Serializable
data class CreateSensorRequestModel(
    val ui: String,
    val value: List<String>
)


@Serializable
data class SendStatusSensorRequestModel(

    val ui: String,
    val token: String,
    val status: Int

)

@Serializable
data class SendAlarmSensorRequestModel(

    val ui: String,
    val token: String,
    val alarm: Int

)


@Serializable
data class SendValueSensorRequestModel(

    val ui: String,
    val token: String,
    val field_id: String,
    val value: String

)