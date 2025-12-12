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
import com.tsd_store.deltahome.domain.models.Device
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
    onAction: (DevicesAction) -> Unit,
    onShowUnknownDetails: (Device) -> Unit,
    onShowLightingDialog: (Device) -> Unit
) {
    val device = uiModel.device
    val category = uiModel.category

    Box {
        when (category) {
            DeviceCategory.EMERGENCY_SENSOR ->
                EmergencySensorCard(device)

            DeviceCategory.WATER_METER ->
                WaterMeterCard(device)

            DeviceCategory.THREE_TARIFF_METER ->
                ThreeTariffMeterCard(device)

            DeviceCategory.COORDINATE_TRACKER ->
                CoordinateTrackerCard(device)

            DeviceCategory.CONTROLLED_LIGHTING ->
                ControlledLightingCard(
                    device = device,
                    onAction = onAction,
                    onShowDetails = { onShowLightingDialog(device) }
                )

            DeviceCategory.GATE ->
                GateCard(device, onAction)

            DeviceCategory.UNKNOWN ->
                UnknownDeviceCard(
                    device = device,
                    onClick = { onShowUnknownDetails(device) }
                )
        }

        if (uiModel.isUpdating) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(18.dp),
                strokeWidth = 2.dp
            )
        }
    }
}