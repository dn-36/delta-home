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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.models.DeviceCategory
import com.tsd_store.deltahome.presentation.DevicesAction
import com.tsd_store.deltahome.presentation.DevicesState
import com.tsd_store.deltahome.presentation.RoomUi

@Composable
fun DevicesScreenContent(
    state: DevicesState,
    snackbarHostState: SnackbarHostState,
    selectedRoomId: String?,
    rooms: List<RoomUi>,
    onChangeRoom: (String?) -> Unit,
    onAddRoom: (String) -> Unit,
    onDeleteRoom: (String) -> Unit,
    onAction: (DevicesAction) -> Unit
) {
    var showAddRoomDialog by remember { mutableStateOf(false) }
    var newRoomName by remember { mutableStateOf("") }

    if (showAddRoomDialog) {
        AlertDialog(
            onDismissRequest = { showAddRoomDialog = false },
            title = { Text("Add room") },
            text = {
                OutlinedTextField(
                    value = newRoomName,
                    onValueChange = { newRoomName = it },
                    singleLine = true,
                    placeholder = { Text("Room name") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val name = newRoomName.trim()
                    if (name.isNotEmpty()) {
                        onAddRoom(name)
                        newRoomName = ""
                        showAddRoomDialog = false
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddRoomDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            SmartHomeTopBar (
                onAddRoomClick = { showAddRoomDialog = true }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            TabsRow(
                rooms = rooms,
                selectedRoomId = selectedRoomId,
                onSelected = onChangeRoom,
                onDeleteRoom = onDeleteRoom
            )

            when {
                state.isLoading && state.devices.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.errorMessage != null && state.devices.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = state.errorMessage)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { onAction(DevicesAction.LoadInitial) }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                else -> {
                    // Пока у нас нет связи device <-> room, показываем все devices,
                    // но сам layout полностью как в твоём примере.
                    DevicesGrid(
                        devices = state.visibleDevices, // из DevicesState (фильтр по категории если есть)
                        selectedRoomId = selectedRoomId,
                        onAction = onAction
                    )
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