package com.tsd_store.deltahome.feature.home.ui.components

import SmartHomeTopBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tsd_store.deltahome.domain.actual_domain.usecases.CreateSensorUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.GetSensorsUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.GetValueSensorUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.SendAlarmSensorUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.SendStatusSensorUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.SendValueSensorUseCase
import com.tsd_store.deltahome.feature.home.viewmodel.HomeAction
import com.tsd_store.deltahome.feature.home.viewmodel.HomeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

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
                onSelected = { roomId -> onAction(HomeAction.ChangeTab(roomId)) },
                onDeleteRoom = { roomId -> onAction(HomeAction.DeleteRoom(roomId)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔽 Блок кнопок для работы с датчиками
            SensorsActionsSection(onAction = onAction)

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading && state.devices.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.CircularProgressIndicator()
                }
            } else {
                DevicesGrid(
                    devices = state.devices.filter { device ->
                        (state.selectedRoomId == null && device.isFavorite) ||
                                (state.selectedRoomId != null && device.roomId == state.selectedRoomId)
                    },
                    selectedRoomId = state.selectedRoomId,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun SensorsActionsSection(
    onAction: (HomeAction) -> Unit
) {

    val scope = rememberCoroutineScope()

    val getSensorsUseCase: GetSensorsUseCase = get()
    val getValueSensorUseCase: GetValueSensorUseCase = get()
    val createSensorUseCase: CreateSensorUseCase = get()
    val sendAlarmSensorUseCase: SendAlarmSensorUseCase = get()
    val sendStatusSensorUseCase: SendStatusSensorUseCase = get()
    val sendValueSensorUseCase: SendValueSensorUseCase = get()

    val ui = "bc47-826o-zqha"

    val token = "u1tz-x4on-zvb3"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Датчики",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Можно заменить на Row + horizontalScroll, если хочешь в ряд
        Button(
            onClick = {

                scope.launch(Dispatchers.IO) {

                    getSensorsUseCase.execute()

                }

                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Получить список датчиков")
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { scope.launch(Dispatchers.IO) {

                getValueSensorUseCase.execute(ui = ui)

            } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Получить значение датчика")
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { scope.launch(Dispatchers.IO) {

                createSensorUseCase.execute(

                    ui = ui,

                    value = listOf("100")

                )

            } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Создать датчик")
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { scope.launch(Dispatchers.IO) {

                sendStatusSensorUseCase.execute(

                    ui = ui, token = token, status = 1

                )

            } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Статус датчика")
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { scope.launch(Dispatchers.IO) {

                sendAlarmSensorUseCase.execute(

                    ui = ui, token = token, alarm = 1

                )

            } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Тревога датчика")
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { scope.launch(Dispatchers.IO) {

                sendValueSensorUseCase.execute(

                    ui = ui, token = token, field_id = "2", value = "250"

                )

            } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Отправить значение")
        }
    }
}