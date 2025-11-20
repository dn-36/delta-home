package com.tsd_store.deltahome.domain.model

enum class RoomType {
    FAVORITES, LIVING_ROOM, KITCHEN, HALL
}


enum class DeviceKind {
    SENSOR_TEMPERATURE,
    LAMP,
    KETTLE,
    LOCK
}

sealed interface Device {
    val id: String
    val name: String
    val roomId: String
    val isFavorite: Boolean
}

enum class SensorType { WATER_LEAK, SMOKE, TEMPERATURE, ELECTRICITY }

data class SensorDevice(
    override val id: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val type: SensorType,
    val value: String,
    val isAlarm: Boolean
) : Device

data class LampDevice(
    override val id: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isOn: Boolean,
    val brightness: Int
) : Device

data class KettleDevice(
    override val id: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isOn: Boolean,
    val targetTemperature: Int
) : Device

data class LockDevice(
    override val id: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isLocked: Boolean
) : Device

data class Room(
    val id: String,
    val name: String
)