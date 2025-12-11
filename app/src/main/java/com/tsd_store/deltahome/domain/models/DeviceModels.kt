package com.tsd_store.deltahome.domain.models

enum class DeviceCategory {
    EMERGENCY_SENSOR,      // Датчик пожарный / газа / утечки
    WATER_METER,           // Счётчик воды
    THREE_TARIFF_METER,    // 3-х тарифный счётчик
    COORDINATE_TRACKER,    // Трекер координат
    CONTROLLED_LIGHTING,   // Управляемое освещение
    GATE,                  // Задвижка
    UNKNOWN                // Все остальные кастомные типы
}

/**
 * Тип поля по смыслу.
 */
enum class FieldValueType {
    BOOLEAN,
    INTEGER,
    STRING,
    FLOAT,
    UNKNOWN
}

/**
 * Единица измерения поля.
 */
data class DeviceUnit(
    val id: Int,
    val name: String,
    val ui: String?
)

/**
 * Нормализованное значение поля.
 * rawValue хранит исходную строку для полного контроля.
 */
data class DeviceFieldValue(
    val rawValue: String,
    val valueBoolean: Boolean? = null,
    val valueNumber: Double? = null,
    val valueText: String? = null,
    val date: String  // Можно заменить на Instant/LocalDateTime, если захочешь
) {
    companion object {
        fun fromRaw(raw: String, date: String, type: FieldValueType): DeviceFieldValue {
            return when (type) {
                FieldValueType.BOOLEAN -> DeviceFieldValue(
                    rawValue = raw,
                    valueBoolean = raw.equals("1") || raw.equals("true", ignoreCase = true),
                    date = date
                )

                FieldValueType.INTEGER, FieldValueType.FLOAT -> DeviceFieldValue(
                    rawValue = raw,
                    valueNumber = raw.toDoubleOrNull(),
                    date = date
                )

                FieldValueType.STRING, FieldValueType.UNKNOWN -> DeviceFieldValue(
                    rawValue = raw,
                    valueText = raw,
                    date = date
                )
            }
        }
    }
}

/**
 * Доменная модель поля устройства.
 */
data class DeviceField(
    val id: Int,
    val name: String,
    val type: FieldValueType,
    val unit: DeviceUnit?,
    val lastValue: DeviceFieldValue? = null
)

/**
 * Доменная модель действия (action) для устройства/типа.
 *
 * Пока на бэке в DTO есть только id/name, поэтому:
 * - command и fieldId делаем опциональными;
 * - в будущем можно добавить сюда enum для command (on_off, add_light и т.п.).
 */
data class DeviceAction(
    val id: Int,
    val name: String,
    val command: String? = null,
    val fieldId: Int? = null
)

/**
 * Доменная модель типа устройства.
 * Включает список полей, действий и категорию (для выбора карточки).
 */
data class DeviceType(
    val id: Int,
    val name: String,
    val systemName: String?,
    val category: DeviceCategory,
    val fields: List<DeviceField>,
    val actions: List<DeviceAction>
)

/**
 * Доменная модель устройства.
 */
data class Device(
    val id: Int,
    val name: String,
    val ui: String,
    val token: String,
    val status: String,
    val alarm: String,
    val type: DeviceType
)