package com.tsd_store.deltahome.feature.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.feature.home.viewmodel.HomeAction
import com.tsd_store.deltahome.domain.old_domain.model.Device
import com.tsd_store.deltahome.domain.old_domain.model.KettleDevice
import com.tsd_store.deltahome.domain.old_domain.model.LampDevice
import com.tsd_store.deltahome.domain.old_domain.model.LockDevice
import com.tsd_store.deltahome.domain.old_domain.model.SensorDevice

@Composable
fun DevicesGrid(
    devices: List<Device>,
    selectedRoomId: String?,
    onAction: (HomeAction) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(devices, key = { it.token }) { device ->
                when (device) {
                    is SensorDevice -> SensorCard(device)
                    is LampDevice -> LampCard(device, onAction)
                    is KettleDevice -> KettleCard(device, onAction)
                    is LockDevice -> LockCard(device, onAction)
                }
            }

            // Круглая "+" карточка — только когда выбрана конкретная комната
            if (selectedRoomId != null) {
                item {
                    AddDeviceCard(
                        roomId = selectedRoomId,
                        onAction = onAction
                    )
                }
            }
        }
    }
}