package com.tsd_store.deltahome.data.remote.old_remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class DeviceDto {
    abstract val token: String
    abstract val name: String
    abstract val roomId: String
    abstract val isFavorite: Boolean
}


@Serializable
@SerialName("sensor")
data class SensorDeviceDto(
    override val token: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val sensorType: String,
    val value: String,
    val isAlarm: Boolean
) : DeviceDto()

@Serializable
@SerialName("lamp")
data class LampDeviceDto(
    override val token: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isOn: Boolean,
    val brightness: Int
) : DeviceDto()


@Serializable
@SerialName("kettle")
data class KettleDeviceDto(
    override val token: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isOn: Boolean,
    val targetTemperature: Int
) : DeviceDto()


@Serializable
@SerialName("lock")
data class LockDeviceDto(
    override val token: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isLocked: Boolean
) : DeviceDto()