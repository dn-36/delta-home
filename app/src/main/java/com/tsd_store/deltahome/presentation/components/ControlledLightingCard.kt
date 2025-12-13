package com.tsd_store.deltahome.presentation.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tsd_store.deltahome.domain.models.Device
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.presentation.DevicesAction


@Composable
fun ControlledLightingCard(
    device: Device,
    onAction: (DevicesAction) -> Unit,
    onShowDetails: () -> Unit,
    onRequestDelete: () -> Unit
) {
    val boolField = device.type.fields.firstOrNull { it.type == FieldValueType.BOOLEAN }
    val brightnessField = device.type.fields.firstOrNull { it.type == FieldValueType.INTEGER }

    val isOn = boolField?.lastValue?.valueBoolean ?: false
    val brightness = (brightnessField?.lastValue?.valueNumber ?: 0.0).toInt()

    BaseDeviceCard(
        title = device.name,
        subtitle = "Light",
        onClick = onShowDetails,
        onLongClick = onRequestDelete,
        trailing = {
            Switch(
                checked = isOn,
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
    ) {
        Text(
            text = "Brightness",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = "$brightness%",
            style = MaterialTheme.typography.titleMedium
        )
    }
}