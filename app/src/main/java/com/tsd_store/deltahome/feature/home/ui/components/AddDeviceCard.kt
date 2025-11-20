package com.tsd_store.deltahome.feature.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.model.DeviceKind
import com.tsd_store.deltahome.feature.home.viewmodel.HomeAction
import io.ktor.websocket.Frame

@Composable
fun AddDeviceCard(
    roomId: String,
    onAction: (HomeAction) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var selectedKind by remember { mutableStateOf(DeviceKind.LAMP) }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Frame.Text("Add device") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        singleLine = true
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Type",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(Modifier.height(4.dp))

                    DeviceTypeOption(
                        title = "Lamp",
                        selected = selectedKind == DeviceKind.LAMP,
                        onClick = { selectedKind = DeviceKind.LAMP }
                    )
                    DeviceTypeOption(
                        title = "Kettle",
                        selected = selectedKind == DeviceKind.KETTLE,
                        onClick = { selectedKind = DeviceKind.KETTLE }
                    )
                    DeviceTypeOption(
                        title = "Lock",
                        selected = selectedKind == DeviceKind.LOCK,
                        onClick = { selectedKind = DeviceKind.LOCK }
                    )
                    DeviceTypeOption(
                        title = "Temperature sensor",
                        selected = selectedKind == DeviceKind.SENSOR_TEMPERATURE,
                        onClick = { selectedKind = DeviceKind.SENSOR_TEMPERATURE }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onAction(
                        HomeAction.AddDevice(
                            roomId = roomId,
                            kind = selectedKind,
                            name = name.trim()
                        )
                    )
                    // сброс и закрытие
                    name = ""
                    selectedKind = DeviceKind.LAMP
                    showDialog = false
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier = Modifier.size(120.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { showDialog = true },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add device",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Add",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}
@Composable
private fun DeviceTypeOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 2.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Spacer(Modifier.width(4.dp))
        Text(title)
    }
}