package com.tsd_store.deltahome.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.DeviceAction
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.domain.models.DeviceField
import com.tsd_store.deltahome.domain.models.DeviceFieldValue
import com.tsd_store.deltahome.domain.models.DeviceType
import com.tsd_store.deltahome.domain.models.DeviceUnit
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.domain.repositories.SensorsRepository

class FakeSensorsRepository : SensorsRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    private var devices: List<Device> = createInitialDevices()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addDevice(
        category: DeviceCategory,
        name: String
    ): ResultDomain<Device> {
        val now = currentIsoDateTime()
        val newId = (devices.maxOfOrNull { it.id } ?: 0) + 1

        // Ищем шаблон устройства по категории, чтобы взять тип/поля/actions
        val template = devices.firstOrNull { it.type.category == category }
        val type = template?.type ?: createTypeForCategory(category, now)

        val uiPrefix = when (category) {
            DeviceCategory.EMERGENCY_SENSOR -> "ui_emergency_"
            DeviceCategory.WATER_METER -> "ui_water_"
            DeviceCategory.THREE_TARIFF_METER -> "ui_three_tariff_"
            DeviceCategory.COORDINATE_TRACKER -> "ui_coord_"
            DeviceCategory.CONTROLLED_LIGHTING -> "ui_light_"
            DeviceCategory.GATE -> "ui_gate_"
            DeviceCategory.UNKNOWN -> "ui_custom_"
        }

        val ui = uiPrefix + newId
        val token = "token_$newId"

        // Обнуляем значения полей в новом девайсе
        val freshFields = type.fields.map { field ->
            field.copy(
                lastValue = field.lastValue?.copy(
                    valueText = field.lastValue.rawValue,
                    date = now
                ) ?: when (field.type) {
                    FieldValueType.BOOLEAN -> DeviceFieldValue.fromRaw("0", now, FieldValueType.BOOLEAN)
                    FieldValueType.INTEGER -> DeviceFieldValue.fromRaw("0", now, FieldValueType.INTEGER)
                    FieldValueType.FLOAT -> DeviceFieldValue.fromRaw("0", now, FieldValueType.FLOAT)
                    FieldValueType.STRING -> DeviceFieldValue.fromRaw("", now, FieldValueType.STRING)
                    FieldValueType.UNKNOWN -> DeviceFieldValue.fromRaw("", now, FieldValueType.UNKNOWN)
                }
            )
        }
        val freshType = type.copy(fields = freshFields)

        val newDevice = Device(
            id = newId,
            name = name,
            ui = ui,
            token = token,
            status = "ok",
            alarm = "no",
            type = freshType
        )

        devices = devices + newDevice
        return ResultDomain.Success(newDevice)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun removeDevice(deviceId: Int): ResultDomain<Unit> {
        val before = devices.size
        devices = devices.filterNot { it.id == deviceId }
        return if (devices.size == before) {
            ResultDomain.Error("Device with id=$deviceId not found")
        } else {
            ResultDomain.Success(Unit)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createTypeForCategory(
        category: DeviceCategory,
        now: String
    ): DeviceType {
        return when (category) {
            DeviceCategory.EMERGENCY_SENSOR -> DeviceType(
                id = 100,
                name = "Датчик пожарный, газа, утечки и пр.",
                systemName = "sensor_work_and_alarm",
                category = DeviceCategory.EMERGENCY_SENSOR,
                fields = emptyList(),
                actions = emptyList()
            )

            DeviceCategory.WATER_METER -> {
                val waterField = DeviceField(
                    id = 110,
                    name = "Показания",
                    type = FieldValueType.INTEGER,
                    unit = DeviceUnit(10, "м³", "unit_m3"),
                    lastValue = DeviceFieldValue.fromRaw("0", now, FieldValueType.INTEGER)
                )
                DeviceType(
                    id = 101,
                    name = "Счётчик воды",
                    systemName = "sensor_water",
                    category = DeviceCategory.WATER_METER,
                    fields = listOf(waterField),
                    actions = emptyList()
                )
            }

            DeviceCategory.THREE_TARIFF_METER -> {
                val t1 = DeviceField(
                    id = 120,
                    name = "Показание 1",
                    type = FieldValueType.INTEGER,
                    unit = DeviceUnit(20, "кВт·ч", "unit_kwh"),
                    lastValue = DeviceFieldValue.fromRaw("0", now, FieldValueType.INTEGER)
                )
                val t2 = t1.copy(id = 121, name = "Показание 2")
                val t3 = t1.copy(id = 122, name = "Показание 3")
                DeviceType(
                    id = 102,
                    name = "3-х тарифный счётчик",
                    systemName = "sensor_tree_data",
                    category = DeviceCategory.THREE_TARIFF_METER,
                    fields = listOf(t1, t2, t3),
                    actions = emptyList()
                )
            }

            DeviceCategory.COORDINATE_TRACKER -> {
                val latField = DeviceField(
                    id = 130,
                    name = "Широта",
                    type = FieldValueType.STRING,
                    unit = null,
                    lastValue = DeviceFieldValue.fromRaw("", now, FieldValueType.STRING)
                )
                val lonField = DeviceField(
                    id = 131,
                    name = "Долгота",
                    type = FieldValueType.STRING,
                    unit = null,
                    lastValue = DeviceFieldValue.fromRaw("", now, FieldValueType.STRING)
                )
                DeviceType(
                    id = 103,
                    name = "Трекер координат",
                    systemName = "sensor_coordinat",
                    category = DeviceCategory.COORDINATE_TRACKER,
                    fields = listOf(latField, lonField),
                    actions = emptyList()
                )
            }

            DeviceCategory.CONTROLLED_LIGHTING -> {
                val boolField = DeviceField(
                    id = 140,
                    name = "Включено",
                    type = FieldValueType.BOOLEAN,
                    unit = null,
                    lastValue = DeviceFieldValue.fromRaw("0", now, FieldValueType.BOOLEAN)
                )
                val brightField = DeviceField(
                    id = 141,
                    name = "Яркость",
                    type = FieldValueType.INTEGER,
                    unit = DeviceUnit(30, "%", "unit_percent"),
                    lastValue = DeviceFieldValue.fromRaw("50", now, FieldValueType.INTEGER)
                )
                val onOff = DeviceAction(
                    id = 440,
                    name = "Вкл/Выкл",
                    command = "on_off",
                    fieldId = boolField.id
                )
                val add = DeviceAction(
                    id = 441,
                    name = "Увеличить яркость",
                    command = "add_light",
                    fieldId = brightField.id
                )
                val cut = DeviceAction(
                    id = 442,
                    name = "Уменьшить яркость",
                    command = "cut_light",
                    fieldId = brightField.id
                )
                DeviceType(
                    id = 104,
                    name = "Управляемое освещение",
                    systemName = "sensor_light",
                    category = DeviceCategory.CONTROLLED_LIGHTING,
                    fields = listOf(boolField, brightField),
                    actions = listOf(onOff, add, cut)
                )
            }

            DeviceCategory.GATE -> {
                val gateField = DeviceField(
                    id = 150,
                    name = "Открыта",
                    type = FieldValueType.BOOLEAN,
                    unit = null,
                    lastValue = DeviceFieldValue.fromRaw("0", now, FieldValueType.BOOLEAN)
                )
                val gateAction = DeviceAction(
                    id = 450,
                    name = "Открыть/Закрыть",
                    command = "on_off",
                    fieldId = gateField.id
                )
                DeviceType(
                    id = 105,
                    name = "Задвижка",
                    systemName = "sensor_gate",
                    category = DeviceCategory.GATE,
                    fields = listOf(gateField),
                    actions = listOf(gateAction)
                )
            }

            DeviceCategory.UNKNOWN -> {
                val field = DeviceField(
                    id = 160,
                    name = "Значение",
                    type = FieldValueType.STRING,
                    unit = null,
                    lastValue = DeviceFieldValue.fromRaw("", now, FieldValueType.STRING)
                )
                DeviceType(
                    id = 106,
                    name = "Кастомный сенсор",
                    systemName = "custom_type_xyz",
                    category = DeviceCategory.UNKNOWN,
                    fields = listOf(field),
                    actions = emptyList()
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun loadDevices(): ResultDomain<List<Device>> {
        // Просто возвращаем текущий список из памяти
        return ResultDomain.Success(devices)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun refreshDeviceValues(ui: String): ResultDomain<Device> {
        // В фейке просто возвращаем то, что есть (можно рандомизировать, если захочешь)
        val device = devices.firstOrNull { it.ui == ui }
            ?: return ResultDomain.Error("Device with ui=$ui not found")

        return ResultDomain.Success(device)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateDeviceStatus(
        ui: String,
        token: String,
        newStatus: String
    ): ResultDomain<Unit> {
        val updated = updateDevice(ui) { dev ->
            dev.copy(status = newStatus)
        } ?: return ResultDomain.Error("Device with ui=$ui not found")

        return ResultDomain.Success(Unit)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateDeviceAlarm(
        ui: String,
        token: String,
        newAlarm: String
    ): ResultDomain<Unit> {
        val updated = updateDevice(ui) { dev ->
            dev.copy(alarm = newAlarm)
        } ?: return ResultDomain.Error("Device with ui=$ui not found")

        return ResultDomain.Success(Unit)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateFieldValue(
        ui: String,
        token: String,
        field: DeviceField,
        newRawValue: String
    ): ResultDomain<Unit> {
        val updated = updateDevice(ui) { dev ->
            dev.updateFieldLocally(field.id, newRawValue)
        } ?: return ResultDomain.Error("Device with ui=$ui not found")

        return ResultDomain.Success(Unit)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun performAction(
        ui: String,
        token: String,
        action: DeviceAction
    ): ResultDomain<Unit> {
        // В фейке просто считаем, что всё ок.
        // По желанию можно менять состояние в зависимости от action.command.
        val device = devices.firstOrNull { it.ui == ui }
            ?: return ResultDomain.Error("Device with ui=$ui not found")

        return ResultDomain.Success(Unit)
    }

    // ---------------- Вспомогательные методы ----------------

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDevice(
        ui: String,
        transform: (Device) -> Device
    ): Device? {
        val current = devices.firstOrNull { it.ui == ui } ?: return null
        val updated = transform(current)
        devices = devices.map { if (it.id == updated.id) updated else it }
        return updated
    }

    /**
     * Локальное обновление поля по id.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun Device.updateFieldLocally(fieldId: Int, newRawValue: String): Device {
        val newFields = type.fields.map { field ->
            if (field.id == fieldId) {
                val newValue = DeviceFieldValue.fromRaw(
                    raw = newRawValue,
                    date = currentIsoDateTime(),
                    type = field.type
                )
                field.copy(lastValue = newValue)
            } else {
                field
            }
        }
        return copy(type = type.copy(fields = newFields))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentIsoDateTime(): String =
        java.time.Instant.now().toString()

    // ---------------- Стартовые данные ----------------

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createInitialDevices(): List<Device> {
        val now = currentIsoDateTime()

        // EMERGENCY_SENSOR
        val emergencyType = DeviceType(
            id = 1,
            name = "Датчик пожарный, газа, утечки и пр.",
            systemName = "sensor_work_and_alarm",
            category = DeviceCategory.EMERGENCY_SENSOR,
            fields = emptyList(),
            actions = emptyList()
        )
        val emergencyDevice = Device(
            id = 1,
            name = "Пожарный датчик в коридоре",
            ui = "ui_emergency_1",
            token = "token_emergency_1",
            status = "normal",
            alarm = "no",
            type = emergencyType
        )

        // WATER_METER
        val waterField = DeviceField(
            id = 10,
            name = "Показания",
            type = FieldValueType.INTEGER,
            unit = DeviceUnit(1, "м³", "unit_m3"),
            lastValue = DeviceFieldValue.fromRaw("123", now, FieldValueType.INTEGER)
        )
        val waterType = DeviceType(
            id = 2,
            name = "Счётчик воды",
            systemName = "sensor_water",
            category = DeviceCategory.WATER_METER,
            fields = listOf(waterField),
            actions = emptyList()
        )
        val waterDevice = Device(
            id = 2,
            name = "Счётчик воды (ванная)",
            ui = "ui_water_1",
            token = "token_water_1",
            status = "ok",
            alarm = "no",
            type = waterType
        )

        // THREE_TARIFF_METER
        val t1 = DeviceField(
            id = 20,
            name = "Показание 1",
            type = FieldValueType.INTEGER,
            unit = DeviceUnit(2, "кВт·ч", "unit_kwh"),
            lastValue = DeviceFieldValue.fromRaw("1000", now, FieldValueType.INTEGER)
        )
        val t2 = DeviceField(
            id = 21,
            name = "Показание 2",
            type = FieldValueType.INTEGER,
            unit = DeviceUnit(2, "кВт·ч", "unit_kwh"),
            lastValue = DeviceFieldValue.fromRaw("500", now, FieldValueType.INTEGER)
        )
        val t3 = DeviceField(
            id = 22,
            name = "Показание 3",
            type = FieldValueType.INTEGER,
            unit = DeviceUnit(2, "кВт·ч", "unit_kwh"),
            lastValue = DeviceFieldValue.fromRaw("300", now, FieldValueType.INTEGER)
        )
        val threeTariffType = DeviceType(
            id = 3,
            name = "3-х тарифный счётчик",
            systemName = "sensor_tree_data",
            category = DeviceCategory.THREE_TARIFF_METER,
            fields = listOf(t1, t2, t3),
            actions = emptyList()
        )
        val threeTariffDevice = Device(
            id = 3,
            name = "Счётчик электроэнергии",
            ui = "ui_three_tariff_1",
            token = "token_three_tariff_1",
            status = "ok",
            alarm = "no",
            type = threeTariffType
        )

        // COORDINATE_TRACKER
        val latField = DeviceField(
            id = 30,
            name = "Широта",
            type = FieldValueType.STRING,
            unit = null,
            lastValue = DeviceFieldValue.fromRaw("55.7558", now, FieldValueType.STRING)
        )
        val lonField = DeviceField(
            id = 31,
            name = "Долгота",
            type = FieldValueType.STRING,
            unit = null,
            lastValue = DeviceFieldValue.fromRaw("37.6173", now, FieldValueType.STRING)
        )
        val coordType = DeviceType(
            id = 4,
            name = "Трекер координат",
            systemName = "sensor_coordinat",
            category = DeviceCategory.COORDINATE_TRACKER,
            fields = listOf(latField, lonField),
            actions = emptyList()
        )
        val coordDevice = Device(
            id = 4,
            name = "GPS трекер машины",
            ui = "ui_coord_1",
            token = "token_coord_1",
            status = "ok",
            alarm = "no",
            type = coordType
        )

        // CONTROLLED_LIGHTING
        val lightBoolField = DeviceField(
            id = 40,
            name = "Включено",
            type = FieldValueType.BOOLEAN,
            unit = null,
            lastValue = DeviceFieldValue.fromRaw("1", now, FieldValueType.BOOLEAN)
        )
        val lightBrightnessField = DeviceField(
            id = 41,
            name = "Яркость",
            type = FieldValueType.INTEGER,
            unit = DeviceUnit(3, "%", "unit_percent"),
            lastValue = DeviceFieldValue.fromRaw("70", now, FieldValueType.INTEGER)
        )
        val lightActionOnOff = DeviceAction(
            id = 400,
            name = "Вкл/Выкл",
            command = "on_off",
            fieldId = lightBoolField.id
        )
        val lightActionAdd = DeviceAction(
            id = 401,
            name = "Увеличить яркость",
            command = "add_light",
            fieldId = lightBrightnessField.id
        )
        val lightActionCut = DeviceAction(
            id = 402,
            name = "Уменьшить яркость",
            command = "cut_light",
            fieldId = lightBrightnessField.id
        )
        val lightType = DeviceType(
            id = 5,
            name = "Управляемое освещение",
            systemName = "sensor_light",
            category = DeviceCategory.CONTROLLED_LIGHTING,
            fields = listOf(lightBoolField, lightBrightnessField),
            actions = listOf(lightActionOnOff, lightActionAdd, lightActionCut)
        )
        val lightDevice = Device(
            id = 5,
            name = "Люстра в гостиной",
            ui = "ui_light_1",
            token = "token_light_1",
            status = "on",
            alarm = "no",
            type = lightType
        )

        // GATE
        val gateField = DeviceField(
            id = 50,
            name = "Открыта",
            type = FieldValueType.BOOLEAN,
            unit = null,
            lastValue = DeviceFieldValue.fromRaw("0", now, FieldValueType.BOOLEAN)
        )
        val gateAction = DeviceAction(
            id = 500,
            name = "Открыть/Закрыть",
            command = "on_off",
            fieldId = gateField.id
        )
        val gateType = DeviceType(
            id = 6,
            name = "Задвижка",
            systemName = "sensor_gate",
            category = DeviceCategory.GATE,
            fields = listOf(gateField),
            actions = listOf(gateAction)
        )
        val gateDevice = Device(
            id = 6,
            name = "Въездные ворота",
            ui = "ui_gate_1",
            token = "token_gate_1",
            status = "closed",
            alarm = "no",
            type = gateType
        )

        // UNKNOWN
        val customField = DeviceField(
            id = 60,
            name = "Температура",
            type = FieldValueType.FLOAT,
            unit = DeviceUnit(4, "°C", "unit_c"),
            lastValue = DeviceFieldValue.fromRaw("23.5", now, FieldValueType.FLOAT)
        )
        val customType = DeviceType(
            id = 7,
            name = "Кастомный сенсор",
            systemName = "custom_type_xyz",
            category = DeviceCategory.UNKNOWN,
            fields = listOf(customField),
            actions = emptyList()
        )
        val customDevice = Device(
            id = 7,
            name = "Устройство",
            ui = "ui_custom_1",
            token = "token_custom_1",
            status = "ok",
            alarm = "no",
            type = customType
        )

        return listOf(
            emergencyDevice,
            waterDevice,
            threeTariffDevice,
            coordDevice,
            lightDevice,
            gateDevice,
            customDevice
        )
    }
}