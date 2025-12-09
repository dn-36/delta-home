package com.tsd_store.deltahome.feature.home.viewmodel

import com.tsd_store.deltahome.domain.old_domain.model.Device
import com.tsd_store.deltahome.domain.old_domain.model.DeviceKind
import com.tsd_store.deltahome.domain.old_domain.model.Room

sealed interface HomeAction {
    data object Load : HomeAction

    // null = Favorites, roomId = конкретная комната
    data class ChangeTab(val roomId: String?) : HomeAction

    // lamp
    data class ToggleLamp(val lampId: String, val isOn: Boolean) : HomeAction
    data class ChangeLampBrightness(val lampId: String, val value: Int) : HomeAction

    // kettle
    data class ToggleKettle(val kettleId: String, val isOn: Boolean) : HomeAction
    data class ChangeKettleTemperature(
        val kettleId: String,
        val temperature: Int
    ) : HomeAction

    // lock
    data class ToggleLock(val lockId: String, val isLocked: Boolean) : HomeAction

    // sensors
    data object RefreshSensors : HomeAction
    data class AddDevice(
        val roomId: String,
        val kind: DeviceKind,
        val name: String
    ) : HomeAction
    data class AddRoom(val name: String) : HomeAction
    data class DeleteRoom(val roomId: String) : HomeAction
}

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val rooms: List<Room> = emptyList(),
    val selectedRoomId: String? = null,   // null -> Favorites tab

    val devices: List<Device> = emptyList()
)

sealed interface HomeEffect {
    data class ShowError(val message: String) : HomeEffect
}