package com.tsd_store.deltahome.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsd_store.deltahome.domain.DeviceRepositoryApi
import com.tsd_store.deltahome.domain.model.Device
import com.tsd_store.deltahome.domain.model.DeviceKind
import com.tsd_store.deltahome.domain.model.SensorDevice
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DeviceRepositoryApi
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effects = Channel<HomeEffect>(Channel.BUFFERED)
    val effects: Flow<HomeEffect> = _effects.receiveAsFlow()

    init {
        dispatch(HomeAction.Load)
    }

    fun dispatch(action: HomeAction) {
        when (action) {
            is HomeAction.Load -> loadData()
            is HomeAction.ChangeTab ->
                _state.update { it.copy(selectedRoomId = action.roomId) }

            is HomeAction.ToggleLamp ->
                updateLamp(action.lampId, action.isOn)

            is HomeAction.ChangeLampBrightness ->
                changeLampBrightness(action.lampId, action.value)

            is HomeAction.ToggleKettle ->
                updateKettle(action.kettleId, action.isOn)

            is HomeAction.ChangeKettleTemperature ->
                updateKettleTemperature(action.kettleId, action.temperature)

            is HomeAction.ToggleLock ->
                updateLock(action.lockId, action.isLocked)

            is HomeAction.RefreshSensors ->
                refreshSensors()

            is HomeAction.AddRoom ->
                addRoom(action.name)

            is HomeAction.DeleteRoom ->
                deleteRoom(action.roomId)

            is HomeAction.AddDevice -> addDevice(action.roomId, action.kind, action.name)
        }
    }

    private fun addDevice(roomId: String, kind: DeviceKind, name: String) =
        viewModelScope.launch {
            try {
                val device = repository.addDevice(roomId, kind, name)
                _state.update { state ->
                    state.copy(devices = state.devices + device)
                }
            } catch (t: Throwable) {
                _effects.send(
                    HomeEffect.ShowError(t.message ?: "Error adding device")
                )
            }
        }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        try {
            val rooms = repository.getRooms()
            val devices = repository.getDevices()
            _state.update {
                it.copy(
                    isLoading = false,
                    rooms = rooms,
                    devices = devices,
                    // по умолчанию – Favorites (selectedRoomId = null)
                )
            }
        } catch (t: Throwable) {
            _state.update { it.copy(isLoading = false, error = t.message) }
            _effects.send(HomeEffect.ShowError(t.message ?: "Unknown error"))
        }
    }

    private fun addRoom(name: String) = viewModelScope.launch {
        try {
            val newRoom = repository.addRoom(name)
            _state.update { state ->
                state.copy(
                    rooms = state.rooms + newRoom,
                    selectedRoomId = newRoom.id
                )
            }
        } catch (t: Throwable) {
            _effects.send(HomeEffect.ShowError(t.message ?: "Error adding room"))
        }
    }

    private fun deleteRoom(roomId: String) = viewModelScope.launch {
        try {
            repository.deleteRoom(roomId)
            _state.update { state ->
                val newRooms = state.rooms.filterNot { it.id == roomId }
                val newDevices = state.devices.filterNot { it.roomId == roomId }
                state.copy(
                    rooms = newRooms,
                    devices = newDevices,
                    selectedRoomId = null   // уходим на Favorites
                )
            }
        } catch (t: Throwable) {
            _effects.send(HomeEffect.ShowError(t.message ?: "Error deleting room"))
        }
    }

    private fun updateKettleTemperature(id: String, temperature: Int) =
        viewModelScope.launch {
            try {
                val kettle = repository.setKettleTemperature(id, temperature)
                updateDeviceInState(kettle)
            } catch (_: Throwable) {
            }
        }

    private fun loadDevices() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        try {
            val devices = repository.getDevices()
            _state.update { it.copy(isLoading = false, devices = devices) }
        } catch (t: Throwable) {
            _state.update { it.copy(isLoading = false, error = t.message) }
            _effects.send(HomeEffect.ShowError(t.message ?: "Unknown error"))
        }
    }

    private fun refreshSensors() = viewModelScope.launch {
        try {
            val updated = repository.refreshSensors()
            _state.update { state ->
                val newDevices = state.devices.map { device ->
                    if (device is SensorDevice) {
                        updated.firstOrNull { it.id == device.id } ?: device
                    } else device
                }
                state.copy(devices = newDevices)
            }
        } catch (_: Throwable) {
        }
    }

    private fun updateLamp(id: String, isOn: Boolean) = viewModelScope.launch {
        try {
            val lamp = repository.setLampPower(id, isOn)
            updateDeviceInState(lamp)
        } catch (_: Throwable) {
        }
    }

    private fun changeLampBrightness(id: String, value: Int) = viewModelScope.launch {
        try {
            val lamp = repository.setLampBrightness(id, value)
            updateDeviceInState(lamp)
        } catch (_: Throwable) {
        }
    }

    private fun updateKettle(id: String, isOn: Boolean) = viewModelScope.launch {
        try {
            val kettle = repository.setKettlePower(id, isOn)
            updateDeviceInState(kettle)
        } catch (_: Throwable) {
        }
    }

    private fun updateLock(id: String, isLocked: Boolean) = viewModelScope.launch {
        try {
            val lock = repository.setLockState(id, isLocked)
            updateDeviceInState(lock)
        } catch (_: Throwable) {
        }
    }

    private fun updateDeviceInState(newDevice: Device) {
        _state.update { state ->
            val list = state.devices.map { old ->
                if (old.id == newDevice.id) newDevice else old
            }
            state.copy(devices = list)
        }
    }
}