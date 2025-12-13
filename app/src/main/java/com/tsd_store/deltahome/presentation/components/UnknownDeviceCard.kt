package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.models.Device

@Composable
fun UnknownDeviceCard(
    device: Device,
    onShowDetails: () -> Unit,
    onRequestDelete: () -> Unit
) {
    BaseDeviceCard(
        title = device.name,
        subtitle = device.type.name,
        onClick = onShowDetails,
        onLongClick = onRequestDelete
    ) {
        Text(
            text = "Tap to see details",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
    }
}