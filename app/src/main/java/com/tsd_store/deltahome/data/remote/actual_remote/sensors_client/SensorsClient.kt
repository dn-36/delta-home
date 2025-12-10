package com.tsd_store.deltahome.data.remote.actual_remote.sensors_client

import com.tsd_store.deltahome.common.network.NetworkError
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.common.network.makeRequest
import com.tsd_store.deltahome.common.network.onSuccess
import com.tsd_store.deltahome.data.remote.NetworkClient
import com.tsd_store.deltahome.data.remote.actual_remote.models.CreateSensorRequestModel
import com.tsd_store.deltahome.data.remote.actual_remote.models.DevicesResponse
import com.tsd_store.deltahome.data.remote.actual_remote.models.SendAlarmSensorRequestModel
import com.tsd_store.deltahome.data.remote.actual_remote.models.SendStatusSensorRequestModel
import com.tsd_store.deltahome.data.remote.actual_remote.models.SendValueSensorRequestModel
import com.tsd_store.deltahome.data.remote.actual_remote.models.ValueSensorResponse
import io.ktor.http.HttpMethod
import kotlinx.serialization.Serializable

class SensorsClient {

    private val SENSORS = "_SENSORS"

    suspend fun getSensors(): ResultNetwork<DevicesResponse, NetworkError> {

        var result: ResultNetwork<DevicesResponse, NetworkError>

        result = makeRequest(
            url ="https://devices.delta.online/api/devices",
            body = null,
            method = HttpMethod.Get,
            client = NetworkClient.client,
            tag = SENSORS + "_GET"
        )

        result.onSuccess { response ->

            println("getListSensors ${response}")

        }

        return result

    }

    suspend fun getValueSensor(ui: String): ResultNetwork<ValueSensorResponse, NetworkError> {

        var result: ResultNetwork<ValueSensorResponse, NetworkError>

        result = makeRequest(
            url ="https://devices.delta.online/api/sensor/$ui",
            body = null,
            method = HttpMethod.Get,
            client = NetworkClient.client,
            tag = SENSORS + "_GET_VALUE_SENSOR_$ui"
        )

        result.onSuccess { response ->

            println("getValueSensor $ui ${response}")

        }

        return result

    }

    suspend fun createSensor(

        ui: String,

        value: List<String>

    ): ResultNetwork<String, NetworkError> {

        val body = CreateSensorRequestModel(

            ui = ui,

            value = value

        )

        var result: ResultNetwork<String, NetworkError>

        result = makeRequest(
            url ="https://devices.delta.online/api/sensor",
            body = body,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = SENSORS + "_CREATE"
        )

        result.onSuccess { response ->

            println("createSensor ${response}")

        }

        return result

    }

    suspend fun sendStatusSensor(

        ui: String,

        token: String,

        status: Int

    ): ResultNetwork<String, NetworkError> {

        val body = SendStatusSensorRequestModel(

            ui = ui,
            token = token,
            status = status

        )

        var result: ResultNetwork<String, NetworkError>

        result = makeRequest(
            url ="https://devices.delta.online/api/sensor-status",
            body = body,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = SENSORS + "_SEND_STATUS_SENSOR"
        )

        result.onSuccess { response ->

            println("sendStatusSensor $ui ${response}")

        }

        return result

    }

    suspend fun sendAlarmSensor(

        ui: String,

        token: String,

        alarm: Int

    ): ResultNetwork<String, NetworkError> {

        val body = SendAlarmSensorRequestModel(

            ui = ui,

            token = token,

            alarm = alarm

        )

        var result: ResultNetwork<String, NetworkError>

        result = makeRequest(
            url ="https://devices.delta.online/api/sensor-alarm",
            body = body,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = SENSORS + "_SEND_ALARM"
        )

        result.onSuccess { response ->

            println("sendAlarmSensor $ui ${response}")

        }

        return result

    }

    suspend fun sendValueSensor(

        ui: String,

        token: String,

        field_id: String,

        value: String

    ): ResultNetwork<String, NetworkError> {

        val body = SendValueSensorRequestModel(

            ui = ui,
            token = token,
            field_id = field_id,
            value = value

        )

        var result: ResultNetwork<String, NetworkError>

        result = makeRequest(
            url ="https://devices.delta.online/api/sensor-value",
            body = body,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = SENSORS + "_SEND_VALUE"
        )

        result.onSuccess { response ->

            println("sendValueSensor $ui ${response}")

        }

        return result

    }

}