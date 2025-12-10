package com.tsd_store.deltahome.domain.old_domain.model

enum class RoomType {
    FAVORITES, LIVING_ROOM, KITCHEN, HALL
}



sealed interface Device {
    val token: String
    val name: String
    val roomId: String
    val isFavorite: Boolean
}

enum class SensorType { WATER_LEAK, SMOKE, TEMPERATURE, ELECTRICITY }

enum class DeviceKind {
    SENSOR_TEMPERATURE,
    SENSOR_WATER_LEAK,
    SENSOR_SMOKE,
    SENSOR_ELECTRICITY,
    LAMP,
    KETTLE,
    LOCK
}
data class SensorDevice(
    override val token: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val type: SensorType,
    val value: String,
    val isAlarm: Boolean
) : Device

data class LampDevice(
    override val token: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isOn: Boolean,
    val brightness: Int
) : Device

data class KettleDevice(
    override val token: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isOn: Boolean,
    val targetTemperature: Int
) : Device

data class LockDevice(
    override val token: String,
    override val name: String,
    override val roomId: String,
    override val isFavorite: Boolean,
    val isLocked: Boolean
) : Device

data class Room(
    val id: String,
    val name: String
)

enum class TypesDevices(name : String) {
    WATER_METER("Счетчик воды"),
    THREE_TARIFF_METER("3-х тарифный счетчик"),
    COORDINATE_TRACKER("Трекер координат"),
    CONTROLLED_LIGHTING("Управляемое освещение")

}