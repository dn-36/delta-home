package com.tsd_store.deltahome.repository.mappers

import com.tsd_store.deltahome.data.remote.old_remote.models.RoomDto
import com.tsd_store.deltahome.domain.old_domain.model.Room

fun RoomDto.toDomain(): Room = Room(
    id = id,
    name = name
)
