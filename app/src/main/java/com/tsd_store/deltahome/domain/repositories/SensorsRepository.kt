package com.tsd_store.deltahome.domain.repositories

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.DeviceAction
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.domain.models.DeviceField

interface LoadDevicesRepository {
    suspend fun loadDevices(): ResultDomain<List<Device>>
}

interface RefreshDeviceValuesRepository {
    suspend fun refreshDeviceValues(ui: String): ResultDomain<Device>
}

interface UpdateDeviceStatusRepository {
    suspend fun updateDeviceStatus(
        ui: String,
        token: String,
        newStatus: String
    ): ResultDomain<Unit>
}

interface UpdateDeviceAlarmRepository {
    suspend fun updateDeviceAlarm(
        ui: String,
        token: String,
        newAlarm: String
    ): ResultDomain<Unit>
}

interface UpdateFieldValueRepository {
    suspend fun updateFieldValue(
        ui: String,
        token: String,
        field: DeviceField,
        newRawValue: String
    ): ResultDomain<Unit>
}

interface PerformActionRepository {
    suspend fun performAction(
        ui: String,
        token: String,
        action: DeviceAction
    ): ResultDomain<Unit>
}

interface AddDeviceRepository {
    suspend fun addDevice(
        category: DeviceCategory,
        name: String
    ): ResultDomain<Device>
}

interface RemoveDeviceRepository {
    suspend fun removeDevice(
        deviceId: Int
    ): ResultDomain<Unit>
}

interface SensorsRepository :
    LoadDevicesRepository,
    RefreshDeviceValuesRepository,
    UpdateDeviceStatusRepository,
    UpdateDeviceAlarmRepository,
    UpdateFieldValueRepository,
    PerformActionRepository,
    AddDeviceRepository,
    RemoveDeviceRepository