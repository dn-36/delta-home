package com.tsd_store.deltahome.feature.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.feature.home.viewmodel.HomeAction
import com.tsd_store.deltahome.domain.model.LampDevice

@Composable
  fun LampCard(device: LampDevice, onAction: (HomeAction) -> Unit) {
    var showDialog by remember(device.id) { mutableStateOf(false) }

    // Диалог изменения яркости — только если лампа включена
    if (showDialog && device.isOn) {
        var localValue by remember(device.id, device.brightness) {
            mutableStateOf(device.brightness.toFloat())
        }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Brightness") },
            text = {
                Column {
                    Text(text = "${localValue.toInt()}%")
                    Spacer(Modifier.height(8.dp))
                    Slider(
                        value = localValue,
                        onValueChange = { localValue = it },
                        valueRange = 0f..100f
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(
                            HomeAction.ChangeLampBrightness(
                                device.id,
                                localValue.toInt()
                            )
                        )
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    val canChangeBrightness = device.isOn

    BaseDeviceCard(
        title = device.name,
        subtitle = if (device.isOn) "On" else "Off",
        trailing = {
            Switch(
                checked = device.isOn,
                onCheckedChange = { onAction(HomeAction.ToggleLamp(device.id, it)) }
            )
        },
        footer = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (canChangeBrightness)
                            Modifier.clickable { showDialog = true }
                        else
                            Modifier
                    )
            ) {
                Text(
                    text = "Brightness",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${device.brightness}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (canChangeBrightness)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
                if (!canChangeBrightness) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Turn on to change",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
        },
        modifier = Modifier.height(150.dp)
    )
}