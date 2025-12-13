package com.tsd_store.deltahome.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tsd_store.deltahome.domain.models.Device

@Composable
fun CoordinateTrackerCard(
    device: Device,
    onRequestDelete: () -> Unit
) {
    val latField = device.type.fields.getOrNull(0)
    val lonField = device.type.fields.getOrNull(1)

    val lat = latField?.lastValue?.valueText ?: latField?.lastValue?.rawValue ?: "-"
    val lon = lonField?.lastValue?.valueText ?: lonField?.lastValue?.rawValue ?: "-"

    BaseDeviceCard(
        title = device.name,
        subtitle = "Location",
        onLongClick = onRequestDelete
    ) {
        Text(
            text = "Coords",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = "$lat, $lon",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}