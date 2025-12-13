package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.tsd_store.deltahome.domain.models.Device

@Composable
fun ThreeTariffMeterCard(
    device: Device,
    onRequestDelete: () -> Unit
) {
    val fields = device.type.fields.take(3)

    BaseDeviceCard(
        title = device.name,
        subtitle = "Electricity"
    ) {
        Column {
            fields.forEachIndexed { index, field ->
                val value = field.lastValue?.valueNumber ?: field.lastValue?.rawValue ?: "-"
                val unit = field.unit?.name ?: ""
                Row{
                    Text(
                        text = "T${index + 1} ",
                       style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,

                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "$value",
                        style = MaterialTheme.typography.bodySmall
                    )
                    if(index ==2){
                    Spacer(Modifier.width(20.dp).height(1.dp))
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodyMedium ,
                        color = Color.Blue
                    )
                        }
                }

                if (index < fields.lastIndex) Spacer(Modifier.height(4.dp))
            }
        }
    }
}

