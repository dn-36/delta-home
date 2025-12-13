package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.data.remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.domain.repositories.AddDeviceRepository

class AddDeviceRepositoryImpl(
    private val client: SensorsClient
) : AddDeviceRepository {
    override suspend fun addDevice(
        category: DeviceCategory,
        name: String,
    ): ResultDomain<Device> {
       client.createSensor(category.ui,)
    }
}