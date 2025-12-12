package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.presentation.DeviceCard
import com.tsd_store.deltahome.presentation.DeviceUiModel
import com.tsd_store.deltahome.presentation.DevicesAction

@Composable
fun DevicesGrid(
    devices: List<DeviceUiModel>,
    selectedRoomId: String?,
    onAction: (DevicesAction) -> Unit
) {
    var detailsUnknownDevice by remember { mutableStateOf<Device?>(null) }
    var lightingDevice by remember { mutableStateOf<Device?>(null) }

    // Диалог для UNKNOWN
    if (detailsUnknownDevice != null) {
        UnknownDeviceDetailsDialog(
            device = detailsUnknownDevice!!,
            onDismiss = { detailsUnknownDevice = null }
        )
    }

    // Диалог для света
    if (lightingDevice != null) {
        LightingControlDialog(
            device = lightingDevice!!,
            onDismiss = { lightingDevice = null },
            onAction = onAction
        )
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(devices, key = { it.device.id }) { uiModel ->
                DeviceCard(
                    uiModel = uiModel,
                    onAction = onAction,
                    onShowUnknownDetails = { device ->
                        detailsUnknownDevice = device
                    },
                    onShowLightingDialog = { device ->
                        lightingDevice = device
                    }
                )
            }
        }
    }
}

@Composable
fun UnknownDeviceDetailsDialog(
    device: Device,
    onDismiss: () -> Unit
) {
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
                if (device.type.fields.isEmpty()) {
                    Text(
                        text = "No fields",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    device.type.fields.forEachIndexed { index, field ->
                        val value = field.lastValue?.valueText
                            ?: field.lastValue?.valueNumber?.toString()
                            ?: field.lastValue?.rawValue
                            ?: "-"

                        val unit = field.unit?.name?.let { " $it" } ?: ""

                        // field name (smaller font)
                        Text(
                            text = field.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        // field value (bigger font)
                        Text(
                            text = value + unit,
                            style = MaterialTheme.typography.titleMedium
                        )

                        if (index < device.type.fields.lastIndex) {
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}