package com.tsd_store.deltahome.feature.home.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.old_domain.model.SensorDevice

@Composable
fun SensorCard(device: SensorDevice) {
    val statusText = if (device.isAlarm) "ALARM" else device.value
    val statusColor =
        if (device.isAlarm) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary

    BaseDeviceCard(
        title = device.name,
        subtitle = device.type.name.lowercase().replaceFirstChar { it.uppercase() },
        footer = {
            Text(
                text = statusText,
                color = statusColor,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        modifier = Modifier.height(120.dp)
    )
}