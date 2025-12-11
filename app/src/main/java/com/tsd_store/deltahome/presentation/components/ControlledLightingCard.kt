package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.presentation.DeviceUiModel
import com.tsd_store.deltahome.presentation.DevicesAction

@Composable
fun ControlledLightingCard(
    uiModel: DeviceUiModel,
    onAction: (DevicesAction) -> Unit
) {
    val device = uiModel.device
    val boolField = device.type.fields.firstOrNull { it.type == FieldValueType.BOOLEAN }
    val brightnessField = device.type.fields.firstOrNull { it.type == FieldValueType.INTEGER }

    val isOn = boolField?.lastValue?.valueBoolean ?: false
    val brightness = (brightnessField?.lastValue?.valueNumber ?: 0.0).toInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(device.name, style = MaterialTheme.typography.body1)
                Text("Управляемое освещение", style = MaterialTheme.typography.body1)
            }
            Switch(
                checked = isOn,
                onCheckedChange = { checked ->
                    boolField?.let {
                        onAction(
                            DevicesAction.OnToggleBooleanField(
                                deviceId = device.id,
                                fieldId = it.id,
                                newValue = checked
                            )
                        )
                    }
                }
            )
        }

        Spacer(Modifier.height(8.dp))

        if (brightnessField != null) {
            Text("Яркость: $brightness%", style = MaterialTheme.typography.body1)
            Slider(
                value = brightness.toFloat(),
                onValueChange = { newValue ->
                    // только локально, без отправки — отправку делаем по onValueChangeFinished
                },
                onValueChangeFinished = {
                    val newInt = brightness // тут можно хранить состояние в remember
                    onAction(
                        DevicesAction.OnChangeIntegerField(
                            deviceId = device.id,
                            fieldId = brightnessField.id,
                            newValue = newInt
                        )
                    )
                },
                valueRange = 0f..100f
            )
        }

        // Кнопки действий (если бэк будет их использовать для add_light, cut_light и т.п.)
        if (device.type.actions.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                device.type.actions.forEach { action ->
                    OutlinedButton(
                        onClick = {
                            onAction(
                                DevicesAction.OnPerformAction(
                                    deviceId = device.id,
                                    actionId = action.id
                                )
                            )
                        }
                    ) {
                        Text(action.name)
                    }
                }
            }
        }
    }
}