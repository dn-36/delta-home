package com.tsd_store.deltahome.repository

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.common.domain.toResultDomain
import com.tsd_store.deltahome.common.network.NetworkError
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.data.remote.old_remote.SmartHomeRemoteDataSource
import com.tsd_store.deltahome.data.remote.old_remote.models.SmartHomeSnapshotDto
import com.tsd_store.deltahome.repository.mappers.toDto
import com.tsd_store.deltahome.domain.old_domain.SmartHomeSyncApi
import com.tsd_store.deltahome.domain.old_domain.model.Device
import com.tsd_store.deltahome.domain.old_domain.model.Room
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SmartHomeSyncImpl(
    private val chatsApi: SmartHomeRemoteDataSource,
    private val json: Json,
   // private val chatUi: String,      // ui чата, куда пишем JSON

) : SmartHomeSyncApi {
    private val UI_CHAT_DEVICE_MANEGEMENT = "zf7dxyir-ex1mqi9j-kyh3qw5c"

    override suspend fun syncState(
        rooms: List<Room>,
        devices: List<Device>
    ): ResultDomain<Unit> {

        val snapshot = SmartHomeSnapshotDto(
            rooms = rooms.map { it.toDto() },
            devices = devices.map { it.toDto() }
        )

        // SmartHomeSnapshotDto -> JSON
        val textJson: String = json.encodeToString(snapshot)


        val result: ResultNetwork<String, NetworkError> = chatsApi.sendMessage(
            chatUI = UI_CHAT_DEVICE_MANEGEMENT,
            text = textJson,

        )

        return result.toResultDomain(
            mapSuccess = { Unit },
            mapError = { it.name }   // или красивее замапить в строку
        )
    }
}