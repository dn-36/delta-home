package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.presentation.DeviceUiModel

@Composable
fun CoordinateTrackerCard(uiModel: DeviceUiModel) {
    val device = uiModel.device
    val latField = device.type.fields.getOrNull(0)
    val lonField = device.type.fields.getOrNull(1)

    val lat = latField?.lastValue?.valueText ?: latField?.lastValue?.rawValue ?: "-"
    val lon = lonField?.lastValue?.valueText ?: lonField?.lastValue?.rawValue ?: "-"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(device.name, style = MaterialTheme.typography.body1)
        Text("Тип: ${device.type.name}", style = MaterialTheme.typography.body1)
        Spacer(Modifier.height(8.dp))

        Text("Широта: $lat")
        Text("Долгота: $lon")
        Spacer(Modifier.height(8.dp))

    }
}