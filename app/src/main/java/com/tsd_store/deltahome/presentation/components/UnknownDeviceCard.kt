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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.presentation.DeviceUiModel
import com.tsd_store.deltahome.presentation.DevicesAction

@Composable
fun UnknownDeviceCard(
    uiModel: DeviceUiModel,
    onAction: (DevicesAction) -> Unit
) {
    val device = uiModel.device

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(device.name, style = MaterialTheme.typography.body1)
        Text(device.type.name, style = MaterialTheme.typography.body1)
        Spacer(Modifier.height(8.dp))

        device.type.fields.forEach { field ->
            val value = field.lastValue?.valueText
                ?: field.lastValue?.valueNumber?.toString()
                ?: field.lastValue?.rawValue
                ?: "-"

            val unit = field.unit?.name?.let { " $it" } ?: ""

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(field.name)
                Text("$value$unit")
            }
        }

        if (device.type.actions.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text("Действия:", style = MaterialTheme.typography.body1)
            Spacer(Modifier.height(4.dp))
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