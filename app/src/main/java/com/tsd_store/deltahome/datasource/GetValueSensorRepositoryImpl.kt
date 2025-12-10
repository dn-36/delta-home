package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.data.remote.actual_remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.domain.actual_domain.models.FieldDtoDomain
import com.tsd_store.deltahome.domain.actual_domain.models.FieldValueDomain
import com.tsd_store.deltahome.domain.actual_domain.models.ValueSensorResponseDomain
import com.tsd_store.deltahome.domain.actual_domain.repositories.GetValueSensorRepositoryApi

class GetValueSensorRepositoryImpl(

    private val sensorsClient: SensorsClient

): GetValueSensorRepositoryApi {

    override suspend fun getValueSensor(ui: String): ResultDomain<ValueSensorResponseDomain> {

        val result = sensorsClient.getValueSensor(ui)

        return when(result) {

            is ResultNetwork.Success -> {

                val item = result.data

                val resultConverter = ValueSensorResponseDomain(

                    ui = item.ui,
                    name = item.name,
                    fields = item.fields.map {

                        FieldDtoDomain(

                            value = it.value.map {

                                FieldValueDomain(

                                    value = it.value,
                                    date = it.date

                                )

                            },
                            name = it.name,
                            type = it.type,
                            unit = it.unit

                        )

                    }

                )

                ResultDomain.Success(resultConverter)

            }

            is ResultNetwork.Error -> {

                ResultDomain.Error(result.error.name)

            }

        }

    }

}