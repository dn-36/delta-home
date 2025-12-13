package com.tsd_store.deltahome.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsd_store.deltahome.common.domain.models.ResultDomain
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.domain.models.DeviceField
import com.tsd_store.deltahome.domain.models.DeviceFieldValue
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.domain.repositories.LoadDevicesRepository
import com.tsd_store.deltahome.domain.repositories.SensorsRepository
import com.tsd_store.deltahome.domain.repositories.UpdateFieldValueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

class DevicesViewModel(
    private val repository: SensorsRepository,
    private val loadDevicesRepository : LoadDevicesRepository,
    private val  updateFieldValueRepository : UpdateFieldValueRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DevicesState(isLoading = true))
    val state: StateFlow<DevicesState> = _state.asStateFlow()

    private val _effects = Channel<DevicesEffect>(Channel.BUFFERED)
    val effects: Flow<DevicesEffect> = _effects.receiveAsFlow()
    private var autoRefreshJob: Job? = null

    fun process(action: DevicesAction) {
        when (action) {
            DevicesAction.LoadInitial -> loadDevices()
            DevicesAction.RefreshAll -> refreshAll()
            is DevicesAction.OnDeviceClick -> toggleExpanded(action.deviceId)
            is DevicesAction.OnToggleBooleanField -> changeBooleanField(action)
            is DevicesAction.OnChangeIntegerField -> changeIntegerField(action)
            is DevicesAction.OnPerformAction -> performAction(action)
            is DevicesAction.OnChangeFilter -> changeFilter(action.category)
            is DevicesAction.AddDeviceConfirm ->
                addDevice(action.category, action.name)
            is DevicesAction.RemoveDeviceConfirm ->
                removeDevice(action.deviceId)
        }
    }


    private fun loadDevices() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        when (val result = loadDevicesRepository.loadDevices()) {
            is ResultDomain.Success -> {
                val devices = result.data
                _state.update { state ->
                    val uiModels = devices.map { it.toUiModel() }
                    state.copy(
                        isLoading = false,
                        devices = uiModels
                    )
                }

                startAutoEmulation()
            }

            is ResultDomain.Error -> {
                _state.update { it.copy(isLoading = false, errorMessage = result.error) }
                _effects.send(DevicesEffect.ShowErrorMessage(result.error))
            }
        }
    }
    private fun addDevice(
        category: DeviceCategory,
        name: String
    ) = viewModelScope.launch {
        when (val result = repository.addDevice(category, name)) {
            is ResultDomain.Success -> {
                val newDevice = result.data
                _state.update { state ->
                    val uiModel = newDevice.toUiModel()
                    state.copy(
                        devices = state.devices + uiModel
                    )
                }
                _effects.send(DevicesEffect.ShowSuccessMessage("Device added"))
            }

            is ResultDomain.Error -> {
                _effects.send(DevicesEffect.ShowErrorMessage(result.error))
            }
        }
    }

    private fun removeDevice(
        deviceId: Int
    ) = viewModelScope.launch {
        when (val result = repository.removeDevice(deviceId)) {
            is ResultDomain.Success -> {
                _state.update { state ->
                    state.copy(
                        devices = state.devices.filterNot { it.device.id == deviceId }
                    )
                }
                _effects.send(DevicesEffect.ShowSuccessMessage("Device removed"))
            }

            is ResultDomain.Error -> {
                _effects.send(DevicesEffect.ShowErrorMessage(result.error))
            }
        }
    }
    private fun refreshAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isRefreshing = true) }

            val currentDevices = _state.value.devices

            val updatedDevices = currentDevices.map { uiModel ->
                when (val result = repository.refreshDeviceValues(uiModel.device.ui)) {
                    is ResultDomain.Success -> uiModel.copy(
                        device = result.data,
                        isUpdating = false,
                        errorMessage = null
                    )

                    is ResultDomain.Error -> {
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

    private fun Device.toUiModel(previous: DeviceUiModel? = null): DeviceUiModel =
        DeviceUiModel(
            device = this,
            category = this.type.category,
            isUpdating = previous?.isUpdating ?: false,
            isExpanded = previous?.isExpanded ?: false,
            errorMessage = previous?.errorMessage
        )

    private fun startAutoEmulation() {
        if (autoRefreshJob != null) return // уже запущено

        autoRefreshJob = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                delay(1000L) // раз в секунду

                _state.update { state ->
                    val updatedDevices = state.devices.map { uiModel ->
                        val updatedDevice = emulateDeviceValues(uiModel.device)
                        uiModel.copy(device = updatedDevice)
                    }
                    state.copy(devices = updatedDevices)
                }
            }
        }
    }

    private suspend fun emulateDeviceValues(device: Device): Device {
        val category = device.type.category

        // Аварийный датчик — не меняем
        if (category == DeviceCategory.EMERGENCY_SENSOR) return device

        val newFields = device.type.fields.map { field ->
            val last = field.lastValue



           val _field =  when (category) {
                DeviceCategory.WATER_METER -> {

                    val current = last?.valueNumber ?: last?.rawValue?.toDoubleOrNull() ?: 0.0
                        val inc = Random.nextDouble(0.0, 0.005)
                        val next = current + inc
                        field.withUpdatedNumber(next, digits = 3)

                }

                DeviceCategory.THREE_TARIFF_METER -> {
                    Log.d("last.valueNumber",last?.valueNumber.toString())


                        val current = last?.valueNumber ?: last?.rawValue?.toDoubleOrNull() ?: 0.0
                        val inc = Random.nextDouble(0.0, 0.09)
                        val next = current + inc
                        field.withUpdatedNumber(next, digits = 2)
                 //   } else field
                }

                DeviceCategory.COORDINATE_TRACKER -> {
                    // Трекер координат — 10.000..200.000, немного дёргаем дробную часть
                    val base = (last?.valueText ?: last?.rawValue)
                        ?.replace(',', '.')
                        ?.toDoubleOrNull()
                        ?: Random.nextDouble(10.0, 200.0)

                    val delta = Random.nextDouble(-0.001, 0.001)
                    val next = (base + delta).coerceIn(10.0, 200.0)

                    field.withUpdatedString(
                        value = formatDouble(next, 5)
                    )
                }

                DeviceCategory.CONTROLLED_LIGHTING -> {field}
                DeviceCategory.GATE -> {field}
                DeviceCategory.UNKNOWN -> {
                    // Все поля — от 1 до 10_000
                    emulateRandomField(field)
                }

                DeviceCategory.EMERGENCY_SENSOR -> field // уже отфильтровано выше
            }
           viewModelScope.launch {
               updateFieldValueRepository.updateFieldValue(
                   device.ui,
                   device.token,
                   field,
                   field.lastValue?.rawValue ?: "-"
               )
           }
            _field


        }


        return device.copy(
            type = device.type.copy(fields = newFields)
        )
    }

    private fun DeviceField.withUpdatedNumber(
        newValue: Double,
        digits: Int = 3
    ): DeviceField {
        val raw = formatDouble(newValue, digits)
        val date = this.lastValue?.date ?: ""
        val newVal = DeviceFieldValue.fromRaw(
            raw = raw,
            date = date,
            type = this.type
        )
        return copy(lastValue = newVal)
    }

    private fun DeviceField.withUpdatedString(
        value: String
    ): DeviceField {
        val date = this.lastValue?.date ?: ""
        val newVal = DeviceFieldValue.fromRaw(
            raw = value,
            date = date,
            type = FieldValueType.STRING
        )
        return copy(lastValue = newVal)
    }

    // RANDOM для CONTROLLED_LIGHTING, GATE, UNKNOWN
    private fun emulateRandomField(field: DeviceField): DeviceField {
        val date = field.lastValue?.date ?: ""
        return when (field.type) {
            FieldValueType.BOOLEAN -> {
                val bool = Random.nextBoolean()
                val raw = if (bool) "1" else "0"
                val newVal = DeviceFieldValue.fromRaw(
                    raw = raw,
                    date = date,
                    type = FieldValueType.BOOLEAN
                )
                field.copy(lastValue = newVal)
            }

            FieldValueType.INTEGER, FieldValueType.FLOAT -> {
                val num = Random.nextDouble(1.0, 10_000.0)
                val raw = if (field.type == FieldValueType.INTEGER) {
                    num.toInt().toString()
                } else {
                    formatDouble(num, 2)
                }
                val newVal = DeviceFieldValue.fromRaw(
                    raw = raw,
                    date = date,
                    type = field.type
                )
                field.copy(lastValue = newVal)
            }

            FieldValueType.STRING, FieldValueType.UNKNOWN -> {
                val num = Random.nextInt(1, 10_001)
                val newVal = DeviceFieldValue.fromRaw(
                    raw = num.toString(),
                    date = date,
                    type = FieldValueType.STRING
                )
                field.copy(lastValue = newVal)
            }
        }
    }

    private fun formatDouble(value: Double, digits: Int): String =
        String.format(Locale.US, "%.${digits}f", value)

    override fun onCleared() {
        super.onCleared()
        autoRefreshJob?.cancel()
    }

}

data class DeviceUiModel(
    val device: Device,
    val category: DeviceCategory,
    val isUpdating: Boolean = false,
    val isExpanded: Boolean = false,
    val errorMessage: String? = null
)

