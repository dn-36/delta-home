package com.tsd_store.deltahome.presentation

import androidx.compose.runtime.Immutable
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.DeviceCategory

data class DevicesState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val devices: List<DeviceUiModel> = emptyList(),
    val selectedCategoryFilter: DeviceCategory? = null
){
    val visibleDevices: List<DeviceUiModel>
        get() = selectedCategoryFilter?.let { filter ->
            devices.filter { it.category == filter }
        } ?: devices
}

// --- Action / Intent

sealed interface DevicesAction {
    object LoadInitial : DevicesAction
    object RefreshAll : DevicesAction

    data class AddDeviceConfirm(
        val category: DeviceCategory,
        val name: String
    ) : DevicesAction

    data class RemoveDeviceConfirm(
        val deviceId: Int
    ) : DevicesAction


    data class OnDeviceClick(val deviceId: Int) : DevicesAction

    data class OnToggleBooleanField(
        val deviceId: Int,
        val fieldId: Int,
        val newValue: Boolean
    ) : DevicesAction

    data class OnChangeIntegerField(
        val deviceId: Int,
        val fieldId: Int,
        val newValue: Int
    ) : DevicesAction

    data class OnPerformAction(
        val deviceId: Int,
        val actionId: Int
    ) : DevicesAction

    data class OnChangeFilter(val category: DeviceCategory?) : DevicesAction
}

// --- Effect (одноразовые события)

sealed interface DevicesEffect {
    data class ShowErrorMessage(val message: String) : DevicesEffect
    data class ShowSuccessMessage(val message: String) : DevicesEffect
}

