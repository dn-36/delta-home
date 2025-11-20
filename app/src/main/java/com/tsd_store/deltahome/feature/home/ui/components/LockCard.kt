package com.tsd_store.deltahome.feature.home.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.feature.home.viewmodel.HomeAction
import com.tsd_store.deltahome.domain.model.LockDevice

@Composable
 fun LockCard(device: LockDevice, onAction: (HomeAction) -> Unit) {
    BaseDeviceCard(
        title = device.name,
        subtitle = if (device.isLocked) "Locked" else "Unlocked",
        trailing = {
            Switch(
                checked = device.isLocked,
                onCheckedChange = { onAction(HomeAction.ToggleLock(device.id, it)) }
            )
        },
        modifier = Modifier.height(120.dp)
    )
}