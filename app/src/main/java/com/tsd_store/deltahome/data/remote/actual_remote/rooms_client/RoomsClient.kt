package com.tsd_store.deltahome.data.remote.actual_remote.rooms_client

import com.tsd_store.deltahome.common.network.NetworkError
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.common.network.makeRequest
import com.tsd_store.deltahome.data.remote.NetworkClient
import com.tsd_store.deltahome.data.remote.actual_remote.rooms_client.models.CreateOrUpdateRoom
import com.tsd_store.deltahome.data.remote.actual_remote.rooms_client.models.DeleteOrAddSensorFromRoomRequestModel
import com.tsd_store.deltahome.data.remote.actual_remote.rooms_client.models.DeleteRoomRequestModel
import io.ktor.http.HttpMethod
import kotlinx.serialization.Serializable

class RoomsClient {

    private val ROOMS = "_ROOMS"

    suspend fun getRooms(): ResultNetwork<String, NetworkError> {

        return makeRequest(
            url ="https://devices.delta.online/api/room",
            body = null,
            method = HttpMethod.Get,
            client = NetworkClient.client,
            tag = ROOMS + "_GET"
        )

    }

    suspend fun deleteRoom(

        token: String,

        ui: String

    ): ResultNetwork<String, NetworkError> {

        val body = DeleteRoomRequestModel(

            token = token,

            ui = ui

        )

        return makeRequest(
            url ="https://devices.delta.online/api/room-delete",
            body = body,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = ROOMS + "_DELETE"
        )

    }

    suspend fun deleteSensorFromRoom(

        token: String,

        ui: String,

        device_token: String,

        device_ui: String

    ): ResultNetwork<String, NetworkError> {

        val body = DeleteOrAddSensorFromRoomRequestModel(

            token = token,

            ui = ui,

            device_token = device_token,

           device_ui =  device_ui

        )

        return makeRequest(
            url ="https://devices.delta.online/api/room-delete-device",
            body = body,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = ROOMS + "_SENSOR_FROM_ROOM_DELETE"
        )

    }

    suspend fun addSensorForRoom(

        token: String,

        ui: String,

        device_token: String,

        device_ui: String

    ): ResultNetwork<String, NetworkError> {

        val body = DeleteOrAddSensorFromRoomRequestModel(

            token = token,

            ui = ui,

            device_token = device_token,

            device_ui =  device_ui

        )

        return makeRequest(
            url ="https://devices.delta.online/api/room-add-device",
            body = body,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = ROOMS + "_SENSOR_FOR_ROOM_ADD"
        )

    }

    suspend fun createOrUpdateRoom(

        token: String,

        ui: String,

        name: String

    ): ResultNetwork<String, NetworkError> {

        val body = CreateOrUpdateRoom(

            token = token,

            ui = ui,

            name = name

        )

        return makeRequest(
            url ="https://devices.delta.online/api/room",
            body = body,
            method = HttpMethod.Post,
            client = NetworkClient.client,
            tag = ROOMS + "_CREATE_OR_UPDATE_ROOM"
        )

    }

}