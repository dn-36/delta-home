package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.presentation.DevicesAction

@Composable
fun GateCard(
    device: Device,
    onAction: (DevicesAction) -> Unit
) {
    val boolField = device.type.fields.firstOrNull { it.type == FieldValueType.BOOLEAN }
    val isOpen = boolField?.lastValue?.valueBoolean ?: false

    BaseDeviceCard(
        title = device.name,
        subtitle = "Gate"
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = "State",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = if (isOpen) "Open" else "Closed",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Switch(
                checked = isOpen,
                onCheckedChange = { checked ->
                    boolField?.let {
                        onAction(
                            DevicesAction.OnToggleBooleanField(
                                deviceId = device.id,
                                fieldId = it.id,
                                newValue = checked
                            )
                        )
                    }
                }
            )
        }
    }
}