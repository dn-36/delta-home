package com.tsd_store.deltahome.repository.mappers

import com.tsd_store.deltahome.data.remote.models.RoomDto
import com.tsd_store.deltahome.domain.model.Room

fun RoomDto.toDomain(): Room = Room(id = id, name = name)
