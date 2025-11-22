package com.tsd_store.deltahome.repository.mappers

import com.tsd_store.deltahome.data.remote.models.RoomDto
import com.tsd_store.deltahome.domain.model.Room

fun Room.toDto(): RoomDto =
    RoomDto(id = id, name = name)