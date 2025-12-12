package com.tsd_store.deltahome.presentation.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.tsd_store.deltahome.domain.models.Device



@Composable
fun EmergencySensorCard(device: Device) {
    val hasAlarm = device.alarm != "no" && device.alarm != "0"

    BaseDeviceCard(
        title = device.name,
        subtitle = device.type.name
    ) {
        Text(
            text = "Alarm",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = if (hasAlarm) "ALARM" else "OK",
            style = MaterialTheme.typography.titleMedium,
            color = if (hasAlarm)
                MaterialTheme.colorScheme.error
            else
                MaterialTheme.colorScheme.primary
        )
    }
}