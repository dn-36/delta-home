package com.tsd_store.deltahome.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AssistChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.presentation.DeviceCard
import com.tsd_store.deltahome.presentation.DevicesAction
import com.tsd_store.deltahome.presentation.DevicesState
import io.ktor.websocket.Frame

@Composable
fun DevicesScreenContent(
    state: DevicesState,
    snackbarHostState: SnackbarHostState,
    onAction: (DevicesAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Frame.Text("Устройства") },
                actions = {
                    IconButton(onClick = { onAction(DevicesAction.RefreshAll) }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Обновить"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {
                state.isLoading && state.devices.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.errorMessage != null && state.devices.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.errorMessage)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { onAction(DevicesAction.LoadInitial) }) {
                            Text("Повторить")
                        }
                    }
                }

                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        FilterRow(
                            selected = state.selectedCategoryFilter,
                            onSelect = { onAction(DevicesAction.OnChangeFilter(it)) }
                        )

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.visibleDevices, key = { it.device.id }) { uiModel ->
                                DeviceCard(
                                    uiModel = uiModel,
                                    onAction = onAction
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterRow(
    selected: DeviceCategory?,
    onSelect: (DeviceCategory?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AssistChip(
            onClick = { onSelect(null) },
            label = { Text("Все") }
        )

    }
}