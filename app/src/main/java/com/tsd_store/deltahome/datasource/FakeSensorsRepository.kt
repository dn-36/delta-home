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
            name = "Неизвестное устройство",
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