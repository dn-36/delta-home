package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Slider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.presentation.DevicesAction

@Composable
fun LightingControlDialog(
    device: Device,
    onDismiss: () -> Unit,
    onAction: (DevicesAction) -> Unit
) {
    val boolField = device.type.fields.firstOrNull { it.type == FieldValueType.BOOLEAN }
    val brightnessField = device.type.fields.firstOrNull { it.type == FieldValueType.INTEGER }

    val initialIsOn = boolField?.lastValue?.valueBoolean ?: false
    val initialBrightness = (brightnessField?.lastValue?.valueNumber ?: 0.0).toInt()

    var isOn by remember(device.id) { mutableStateOf(initialIsOn) }
    var brightness by remember(device.id) { mutableStateOf(initialBrightness.toFloat()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = device.name,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column {
                // On / Off
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Power",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            text = if (isOn) "On" else "Off",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    androidx.compose.material3.Switch(
                        checked = isOn,
                        onCheckedChange = { isOn = it }
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Brightness
                Text(
                    text = "Brightness",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "${brightness.toInt()}%",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                Slider(
                    value = brightness,
                    onValueChange = { brightness = it },
                    enabled = isOn,
                    valueRange = 0f..100f
                )
                if (!isOn) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Turn on to change brightness",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // отправляем только то, что реально изменилось
                    if (boolField != null && isOn != initialIsOn) {
                        onAction(
                            DevicesAction.OnToggleBooleanField(
                                deviceId = device.id,
                                fieldId = boolField.id,
                                newValue = isOn
                            )
                        )
                    }

                    if (brightnessField != null && brightness.toInt() != initialBrightness) {
                        onAction(
                            DevicesAction.OnChangeIntegerField(
                                deviceId = device.id,
                                fieldId = brightnessField.id,
                                newValue = brightness.toInt()
                            )
                        )
                    }

                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}