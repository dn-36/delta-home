package com.tsd_store.deltahome.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    val id: String,
    val name: String,
    val roomId: String,
    val isFavorite: Boolean,

    val type: String,               // "sensor", "lamp", "kettle", "lock"

    // sensor
    val sensorType: String? = null, // "WATER_LEAK", "SMOKE", "TEMPERATURE", "ELECTRICITY"
    val value: String? = null,
    val isAlarm: Boolean? = null,

    // lamp
    val isOn: Boolean? = null,
    val brightness: Int? = null,

    // kettle
    val targetTemperature: Int? = null,

    // lock
    val isLocked: Boolean? = null
)