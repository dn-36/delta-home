package com.tsd_store.deltahome.datasource

import com.tsd_store.deltahome.common.network.EmptyResult
import com.tsd_store.deltahome.common.network.NetworkError
import com.tsd_store.deltahome.common.network.ResultNetwork
import com.tsd_store.deltahome.common.network.map
import com.tsd_store.deltahome.common.network.onSuccess
import com.tsd_store.deltahome.data.remote.SmartHomeJsonFakeBackend
import com.tsd_store.deltahome.data.remote.models.SmartHomeSnapshotDto

class SmartHomeJsonDataSource(
    private val backend: SmartHomeJsonFakeBackend
) {

    fun loadSnapshot(): ResultNetwork<SmartHomeSnapshotDto, NetworkError> {
        return backend.parseSnapshot()
    }

    fun saveSnapshot(snapshot: SmartHomeSnapshotDto): EmptyResult<NetworkError> {
        return backend.saveSnapshot(snapshot)
    }

    /**
     * Обновляем снапшот атомарно: загрузили → изменили → сохранили.
     */
    fun updateSnapshot(
        transform: (SmartHomeSnapshotDto) -> SmartHomeSnapshotDto
    ): ResultNetwork<SmartHomeSnapshotDto, NetworkError> {
        return backend.parseSnapshot()
            .map(transform)                // Result<SmartHomeSnapshotDto, NetworkError>
            .onSuccess { newSnapshot ->
                backend.saveSnapshot(newSnapshot)
            }
    }
}