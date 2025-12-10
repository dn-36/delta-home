package com.tsd_store.deltahome.feature.home.ui.components

import SmartHomeTopBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tsd_store.deltahome.feature.home.viewmodel.HomeAction
import com.tsd_store.deltahome.feature.home.viewmodel.HomeState

@Composable
fun HomeScreenContent(
    state: HomeState,
    onAction: (HomeAction) -> Unit
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
                        onAction(HomeAction.AddRoom(name))
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
            SmartHomeTopBar(
                onAddRoomClick = { showAddRoomDialog = true }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            TabsRow(
                rooms = state.rooms,
                selectedRoomId = state.selectedRoomId,
                onSelected = {
                   // roomId -> onAction(HomeAction.ChangeTab(roomId))
                             },
                onDeleteRoom = {
                 //   roomId -> onAction(HomeAction.DeleteRoom(roomId))
                }
            )

            if (state.isLoading && state.devices.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.CircularProgressIndicator()
                }
            } else {
                DevicesGrid(
                    devices = state.devices/*.filter { device ->
                        (state.selectedRoomId == null && device.isFavorite) ||
                                (state.selectedRoomId != null && device.roomId == state.selectedRoomId)
                    }*/,
                    selectedRoomId = state.selectedRoomId,
                    onAction = onAction
                )
            }
        }
    }
}