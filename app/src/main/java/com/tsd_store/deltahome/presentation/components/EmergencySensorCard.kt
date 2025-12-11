package com.tsd_store.deltahome.presentation.components

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerDefaults.backgroundColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.presentation.DeviceUiModel


@Composable
fun EmergencySensorCard(uiModel: DeviceUiModel) {
    val device = uiModel.device
    val hasAlarm = device.alarm != "no" && device.alarm != "0"

 /*   val backgroundColor =
        if (hasAlarm) MaterialTheme.colorScheme.errorContainer
        else MaterialTheme.colorScheme.surfaceVariant*/

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding (16.dp)
    ) {
        Text(text = device.name, style = MaterialTheme.typography.body1)
        Text(text = device.type.name, style = MaterialTheme.typography.body1)
        Spacer(Modifier.height(8.dp))
        Text(text = "Статус: ${device.status}")
        Text(
            text = if (hasAlarm) "Тревога: АКТИВНА" else "Тревога: нет",
          //  color = if (hasAlarm) Color.RED else Color.WHITE
        )
    }
}