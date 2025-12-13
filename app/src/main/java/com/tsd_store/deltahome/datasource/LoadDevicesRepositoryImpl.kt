package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.domain.toResultDomain
import com.tsd_store.deltahome.data.remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.data.remote.sensors_client.models.DeviceActionDto
import com.tsd_store.deltahome.data.remote.sensors_client.models.DeviceDto
import com.tsd_store.deltahome.data.remote.sensors_client.models.DeviceFieldDto
import com.tsd_store.deltahome.data.remote.sensors_client.models.DeviceTypeDto
import com.tsd_store.deltahome.data.remote.sensors_client.models.UnitDto
import com.tsd_store.deltahome.datasource.mappers.toDomain
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.DeviceAction
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.domain.models.DeviceField
import com.tsd_store.deltahome.domain.models.DeviceType
import com.tsd_store.deltahome.domain.models.DeviceUnit
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.domain.repositories.LoadDevicesRepository

class LoadDevicesRepositoryImpl(
    private val sensorsClient : SensorsClient
): LoadDevicesRepository {
    override suspend fun loadDevices(): ResultDomain<List<Device>> {
       return sensorsClient.getSensors().toResultDomain(
           {
                 it.map {
                  it.toDomain()
                 }
           },
           {
               it.name
           }
       )
    }



}



