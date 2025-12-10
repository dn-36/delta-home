package com.tsd_store.deltahome.data.remote.actual_remote.sensors_client

import com.tsd_store.deltahome.common.network.NetworkError
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.common.network.makeRequest
import com.tsd_store.deltahome.common.network.onSuccess
import com.tsd_store.deltahome.data.remote.NetworkClient
import com.tsd_store.deltahome.data.remote.actual_remote.DevicesResponse
import com.tsd_store.deltahome.data.remote.actual_remote.ValueSensorResponse
import io.ktor.http.HttpMethod

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

        val body = mapOf(

            "ui" to ui,

            "value" to value

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

        val body = mapOf(

            "ui" to ui,

            "token" to token,

            "status" to status

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

        val body = mapOf(

            "ui" to ui,

            "token" to token,

            "alarm" to alarm

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

        val body = mapOf(

            "ui" to ui,

            "token" to token,

            "field_id" to field_id,

            "value" to value

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