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
import com.tsd_store.deltahome.domain.old_domain.model.KettleDevice

@Composable
fun KettleCard(device: KettleDevice, onAction: (HomeAction) -> Unit) {
    var showTempDialog by remember(device.token) { mutableStateOf(false) }

    if (showTempDialog) {
        var localTemp by remember(device.token, device.targetTemperature) {
            mutableStateOf(device.targetTemperature.toFloat())
        }

        AlertDialog(
            onDismissRequest = { showTempDialog = false },
            title = { Text("Temperature") },
            text = {
                Column {
                    Text("${localTemp.toInt()} °C")
                    Spacer(Modifier.height(8.dp))
                    Slider(
                        value = localTemp,
                        onValueChange = { localTemp = it },
                        valueRange = 40f..100f
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                      /*  onAction(
                            HomeAction.ChangeKettleTemperature(
                                device.token,
                                localTemp.toInt()
                            )
                        )*/
                        showTempDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTempDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    BaseDeviceCard(
        title = device.name,
        subtitle = if (device.isOn) "Boiling" else "Ready",
        trailing = {
            Switch(
                checked = device.isOn,
                onCheckedChange = {
                 //   onAction(HomeAction.ToggleKettle(device.token, it))
                }
            )
        },
        footer = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTempDialog = true }
            ) {
                Text(
                    text = "Temperature",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${device.targetTemperature} °C",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        modifier = Modifier.height(140.dp)
    )
}