package com.tsd_store.deltahome.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tsd_store.deltahome.domain.models.Device

@Composable
fun WaterMeterCard(device: Device) {
    val field = device.type.fields.firstOrNull()
    val value = field?.lastValue?.valueNumber ?: field?.lastValue?.rawValue ?: "-"
    val unit = field?.unit?.name ?: ""

    BaseDeviceCard(
        title = device.name,
        subtitle = "Water"
    ) {
        Text(
            text = "Value",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = "$value $unit",
            style = MaterialTheme.typography.titleMedium
        )
    }
}