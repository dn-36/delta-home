package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.data.remote.actual_remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.domain.actual_domain.models.DeviceActionDtoDomain
import com.tsd_store.deltahome.domain.actual_domain.models.DeviceDtoDomain
import com.tsd_store.deltahome.domain.actual_domain.models.DeviceFieldDtoDomain
import com.tsd_store.deltahome.domain.actual_domain.models.DeviceTypeDtoDomain
import com.tsd_store.deltahome.domain.actual_domain.models.DevicesResponseDomain
import com.tsd_store.deltahome.domain.actual_domain.models.UnitDtoDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.GetSensorsRepositoryApi

class GetSensorsRepositoryImpl(

    private val sensorsClient: SensorsClient

): GetSensorsRepositoryApi {

    override suspend fun getSensors(): ResultDomain<DevicesResponseDomain> {

        val result = sensorsClient.getSensors()

        return when(result) {

            is ResultNetwork.Success -> {

                val resultConverter: DevicesResponseDomain = result.data.map {

                    DeviceDtoDomain(

                        id = it.id,
                        name = it.name,
                        ui = it.ui,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt,
                        typeId = it.typeId,
                        token = it.token,
                        status = it.status,
                        alarm = it.alarm,
                        type = DeviceTypeDtoDomain(

                            id = it.type.id,
                            name = it.type.name,
                            createdAt = it.type.createdAt,
                            updatedAt = it.type.updatedAt,
                            systemName = it.type.systemName,
                            fields = it.type.fields.map {

                                DeviceFieldDtoDomain(

                                    id = it.id,
                                    name = it.name,
                                    type = it.type,
                                    deviceTypeId = it.deviceTypeId,
                                    createdAt = it.createdAt,
                                    updatedAt = it.updatedAt,
                                    unitId = it.unitId,
                                    unit = if (it.unit != null)

                                        UnitDtoDomain(

                                            id = it.unit.id,
                                            name = it.unit.name,
                                            ui = it.unit.ui,
                                            createdAt = it.unit.createdAt,
                                            updatedAt = it.unit.updatedAt

                                        )

                                    else null

                                )

                            },

                            actions = it.type.actions.map {

                                DeviceActionDtoDomain(

                                    id = it.id,
                                    name = it.name

                                )

                            }

                        )

                    )

                }

                ResultDomain.Success(resultConverter)

            }

            is ResultNetwork.Error -> {

                ResultDomain.Error(result.error.name)

            }

        }

    }

}