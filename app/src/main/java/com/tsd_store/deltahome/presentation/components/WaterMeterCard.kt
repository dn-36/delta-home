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
fun WaterMeterCard(uiModel: DeviceUiModel) {
    val device = uiModel.device
    val field = device.type.fields.firstOrNull()
    val value = field?.lastValue?.valueNumber ?: field?.lastValue?.rawValue ?: "-"
    val unit = field?.unit?.name ?: ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(device.name, style = MaterialTheme.typography.body1)
        Text("Тип: ${device.type.name}", style = MaterialTheme.typography.body1)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Показание: $value $unit",
            style = MaterialTheme.typography.body1
        )
    }
}