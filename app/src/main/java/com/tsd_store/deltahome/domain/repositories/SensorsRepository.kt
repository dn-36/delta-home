package com.tsd_store.deltahome.domain.repositories

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.DeviceAction
import com.tsd_store.deltahome.domain.models.DeviceField

interface SensorsRepository {

    /**
     * Загрузить список устройств (без значений или с ленивой подзагрузкой — см. реализацию).
     */
    suspend fun loadDevices(): ResultDomain<List<Device>>

    /**
     * Обновить значения полей для конкретного устройства.
     */
    suspend fun refreshDeviceValues(ui: String): ResultDomain<Device>

    /**
     * Обновить статус устройства (включено/выключено/старт/стоп).
     * Маппинг на /api/sensor-status — в data-слое.
     */
    suspend fun updateDeviceStatus(
        ui: String,
        token: String,
        newStatus: String
    ): ResultDomain<Unit>

    /**
     * Обновить тревогу устройства (alarm).
     * Маппинг на /api/sensor-alarm — в data-слое.
     */
    suspend fun updateDeviceAlarm(
        ui: String,
        token: String,
        newAlarm: String
    ): ResultDomain<Unit>

    /**
     * Отправить новое значение поля.
     * Маппинг на /api/sensor-value — в data-слое.
     */
    suspend fun updateFieldValue(
        ui: String,
        token: String,
        field: DeviceField,
        newRawValue: String
    ): ResultDomain<Unit>

    /**
     * Выполнить действие (action) устройства.
     * Подразумевается отдельный action-endpoint.
     */
    suspend fun performAction(
        ui: String,
        token: String,
        action: DeviceAction
    ): ResultDomain<Unit>
}