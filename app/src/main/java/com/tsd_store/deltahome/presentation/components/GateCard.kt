package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.models.FieldValueType
import com.tsd_store.deltahome.presentation.DeviceUiModel
import com.tsd_store.deltahome.presentation.DevicesAction

@Composable
fun GateCard(
    uiModel: DeviceUiModel,
    onAction: (DevicesAction) -> Unit
) {
    val device = uiModel.device
    val boolField = device.type.fields.firstOrNull { it.type == FieldValueType.BOOLEAN }
    val isOpen = boolField?.lastValue?.valueBoolean ?: false

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(device.name, style = MaterialTheme.typography.body1)
        Text("Задвижка", style = MaterialTheme.typography.body1)
        Spacer(Modifier.height(8.dp))

        Text(
            text = if (isOpen) "Состояние: ОТКРЫТА" else "Состояние: ЗАКРЫТА",
            style = MaterialTheme.typography.body1
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                boolField?.let {
                    onAction(
                        DevicesAction.OnToggleBooleanField(
                            deviceId = device.id,
                            fieldId = it.id,
                            newValue = !isOpen
                        )
                    )
                }
            }
        ) {
            Text(if (isOpen) "Закрыть" else "Открыть")
        }
    }
}