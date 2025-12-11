package com.tsd_store.deltahome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.domain.models.DeviceField
import com.tsd_store.deltahome.domain.models.DeviceFieldValue
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.domain.repositories.SensorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DevicesViewModel(
    private val repository: SensorsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DevicesState(isLoading = true))
    val state: StateFlow<DevicesState> = _state.asStateFlow()

    private val _effects = Channel<DevicesEffect>(Channel.BUFFERED)
    val effects: Flow<DevicesEffect> = _effects.receiveAsFlow()

    fun process(action: DevicesAction) {
        when (action) {
            DevicesAction.LoadInitial -> loadDevices()
            DevicesAction.RefreshAll -> refreshAll()
            is DevicesAction.OnDeviceClick -> toggleExpanded(action.deviceId)
            is DevicesAction.OnToggleBooleanField -> changeBooleanField(action)
            is DevicesAction.OnChangeIntegerField -> changeIntegerField(action)
            is DevicesAction.OnPerformAction -> performAction(action)
            is DevicesAction.OnChangeFilter -> changeFilter(action.category)
        }
    }
    private fun loadDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = repository.loadDevices()) {

                is ResultDomain.Success -> {

                    val uiModels = result.data.map { DeviceUiModel(device = it) }
                    _state.update { it.copy(isLoading = false, devices = uiModels) }
                }

                is ResultDomain.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.error
                        )
                    }
                    _effects.send(DevicesEffect.ShowErrorMessage(result.error))
                }
            }
        }
    }

    private fun refreshAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isRefreshing = true) }

            val currentDevices = _state.value.devices

            val updatedDevices = currentDevices.map { uiModel ->
                when (val result = repository.refreshDeviceValues(uiModel.device.ui)) {
                    is ResultDomain.Success -> uiModel.copy(device = result.data, isUpdating = false)
                    is ResultDomain.Error -> {
                        // Показываем ошибку локально на карточке
                        uiModel.copy(
                            isUpdating = false,
                            errorMessage = result.error
                        ).also {
                            viewModelScope.launch {
                                _effects.send(
                                    DevicesEffect.ShowErrorMessage(
                                        "Ошибка обновления ${uiModel.device.name}: ${result.error}"
                                    )
                                )
                            }
                        }
                    }
                }
            }

            _state.update {
                it.copy(
                    isRefreshing = false,
                    devices = updatedDevices
                )
            }
        }
    }

    private fun toggleExpanded(deviceId: Int) {
        _state.update { state ->
            state.copy(
                devices = state.devices.map {
                    if (it.device.id == deviceId) it.copy(isExpanded = !it.isExpanded) else it
                }
            )
        }
    }

    private fun changeFilter(category: DeviceCategory?) {
        _state.update { it.copy(selectedCategoryFilter = category) }
    }

    // --- Изменение bool-поля (переключатель) ---

    private fun changeBooleanField(action: DevicesAction.OnToggleBooleanField) {
        val uiModel = _state.value.devices.find { it.device.id == action.deviceId } ?: return
        val device = uiModel.device
        val field = device.type.fields.find { it.id == action.fieldId } ?: return

        // Оптимистичное обновление
        val updatedDevice = device.updateFieldLocally(field, action.newValue)
        updateDeviceUiModel(updatedDevice, isUpdating = true)

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateFieldValue(
                ui = device.ui,
                token = device.token,
                field = field,
                newRawValue = if (action.newValue) "1" else "0"
            )

            when (result) {
                is ResultDomain.Success -> {
                    // снимаем флаг загрузки
                    updateDeviceUiModel(updatedDevice, isUpdating = false)
                    _effects.send(
                        DevicesEffect.ShowSuccessMessage("Устройство ${device.name} обновлено")
                    )
                }

                is ResultDomain.Error -> {
                    // откат
                    val rolledBack = device.updateFieldLocally(field, !(action.newValue))
                    updateDeviceUiModel(rolledBack, isUpdating = false, error = result.error)
                    _effects.send(
                        DevicesEffect.ShowErrorMessage("Ошибка: ${result.error}")
                    )
                }
            }
        }
    }

    // --- Изменение integer-поля (яркость и т.п.) ---

    private fun changeIntegerField(action: DevicesAction.OnChangeIntegerField) {
        val uiModel = _state.value.devices.find { it.device.id == action.deviceId } ?: return
        val device = uiModel.device
        val field = device.type.fields.find { it.id == action.fieldId } ?: return

        val updatedDevice = device.updateFieldLocally(field, action.newValue)
        updateDeviceUiModel(updatedDevice, isUpdating = true)

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateFieldValue(
                ui = device.ui,
                token = device.token,
                field = field,
                newRawValue = action.newValue.toString()
            )
            when (result) {
                is ResultDomain.Success -> {
                    updateDeviceUiModel(updatedDevice, isUpdating = false)
                    _effects.send(
                        DevicesEffect.ShowSuccessMessage("Значение обновлено")
                    )
                }

                is ResultDomain.Error -> {
                    // откат — проще всего запросить обновленные значения с бэка
                    when (val refresh = repository.refreshDeviceValues(device.ui)) {
                        is ResultDomain.Success -> {
                            updateDeviceUiModel(refresh.data, isUpdating = false, error = result.error)
                        }

                        is ResultDomain.Error -> {
                            updateDeviceUiModel(device, isUpdating = false, error = result.error)
                        }
                    }
                    _effects.send(
                        DevicesEffect.ShowErrorMessage("Ошибка: ${result.error}")
                    )
                }
            }
        }
    }

    private fun performAction(action: DevicesAction.OnPerformAction) {
        val uiModel = _state.value.devices.find { it.device.id == action.deviceId } ?: return
        val device = uiModel.device
        val deviceAction = device.type.actions.find { it.id == action.actionId } ?: return

        updateDeviceUiModel(device, isUpdating = true)

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.performAction(
                ui = device.ui,
                token = device.token,
                action = deviceAction
            )
            when (result) {
                is ResultDomain.Success -> {
                    updateDeviceUiModel(device, isUpdating = false)
                    _effects.send(
                        DevicesEffect.ShowSuccessMessage("Команда отправлена")
                    )
                    // По желанию — сразу обновить значения
                    refreshDevice(device.ui)
                }

                is ResultDomain.Error -> {
                    updateDeviceUiModel(device, isUpdating = false, error = result.error)
                    _effects.send(
                        DevicesEffect.ShowErrorMessage("Ошибка: ${result.error}")
                    )
                }
            }
        }
    }

    private fun refreshDevice(ui: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.refreshDeviceValues(ui)) {
                is ResultDomain.Success -> updateDeviceUiModel(result.data, isUpdating = false)
                is ResultDomain.Error   -> _effects.send(DevicesEffect.ShowErrorMessage(result.error))
            }
        }
    }

    private fun updateDeviceUiModel(
        updatedDevice: Device,
        isUpdating: Boolean,
        error: String? = null
    ) {
        _state.update { state ->
            state.copy(
                devices = state.devices.map {
                    if (it.device.id == updatedDevice.id) {
                        it.copy(
                            device = updatedDevice,
                            isUpdating = isUpdating,
                            errorMessage = error
                        )
                    } else it
                }
            )
        }
    }

    // --- Локальное обновление значения поля в доменной модели (для оптимистического UI) ---

    private fun Device.updateFieldLocally(field: DeviceField, newValue: Boolean): Device {
        val newVal = DeviceFieldValue.fromRaw(
            raw = if (newValue) "1" else "0",
            date = field.lastValue?.date ?: "",
            type = FieldValueType.BOOLEAN
        )
        val newFields = type.fields.map {
            if (it.id == field.id) it.copy(lastValue = newVal) else it
        }
        return copy(type = type.copy(fields = newFields))
    }

    private fun Device.updateFieldLocally(field: DeviceField, newValue: Int): Device {
        val newVal = DeviceFieldValue.fromRaw(
            raw = newValue.toString(),
            date = field.lastValue?.date ?: "",
            type = FieldValueType.INTEGER
        )
        val newFields = type.fields.map {
            if (it.id == field.id) it.copy(lastValue = newVal) else it
        }
        return copy(type = type.copy(fields = newFields))
    }
}