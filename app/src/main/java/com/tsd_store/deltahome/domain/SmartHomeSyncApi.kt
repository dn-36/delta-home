package com.tsd_store.deltahome.domain

import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.model.Device
import com.tsd_store.deltahome.domain.model.Room

interface SmartHomeSyncApi {

    /**
     * Синхронизировать текущее состояние умного дома с сервером.
     * rooms + devices -> SmartHomeSnapshotDto -> JSON -> sendMessage()
     */
    suspend fun syncState(
        rooms: List<Room>,
        devices: List<Device>
    ): ResultDomain<Unit>
}