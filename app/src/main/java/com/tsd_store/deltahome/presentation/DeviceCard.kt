package com.tsd_store.deltahome.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.presentation.components.ControlledLightingCard
import com.tsd_store.deltahome.presentation.components.CoordinateTrackerCard
import com.tsd_store.deltahome.presentation.components.EmergencySensorCard
import com.tsd_store.deltahome.presentation.components.GateCard
import com.tsd_store.deltahome.presentation.components.ThreeTariffMeterCard
import com.tsd_store.deltahome.presentation.components.UnknownDeviceCard
import com.tsd_store.deltahome.presentation.components.WaterMeterCard

@Composable
fun DeviceCard(
    uiModel: DeviceUiModel,
    onAction: (DevicesAction) -> Unit
) {
    val device = uiModel.device
    val category = uiModel.category

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAction(DevicesAction.OnDeviceClick(device.id)) },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            when (category) {
                DeviceCategory.EMERGENCY_SENSOR ->
                    EmergencySensorCard(uiModel)

                DeviceCategory.WATER_METER ->
                    WaterMeterCard(uiModel)

                DeviceCategory.THREE_TARIFF_METER ->
                    ThreeTariffMeterCard(uiModel)

                DeviceCategory.COORDINATE_TRACKER ->
                    CoordinateTrackerCard(uiModel)

                DeviceCategory.CONTROLLED_LIGHTING ->
                    ControlledLightingCard(uiModel, onAction)

                DeviceCategory.GATE ->
                    GateCard(uiModel, onAction)

                DeviceCategory.UNKNOWN ->
                    UnknownDeviceCard(uiModel, onAction)
            }

            if (uiModel.isUpdating) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}