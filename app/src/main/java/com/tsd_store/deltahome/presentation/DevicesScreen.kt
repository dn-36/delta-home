package com.tsd_store.deltahome.presentation

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import com.tsd_store.deltahome.presentation.components.DevicesScreenContent
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.mp.KoinPlatform.getKoin

class DevicesScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: DevicesViewModel = remember { DevicesViewModel(getKoin().get()) }

        val state by viewModel.state.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }

        // --- локальное состояние комнат (чисто UI) ---
        var rooms by remember {
            mutableStateOf(
                listOf(
                    RoomUi(id = "room_1", name = "Living room"),
                    RoomUi(id = "room_2", name = "Kitchen")
                )
            )
        }
        var selectedRoomId by remember { mutableStateOf<String?>(null) } // null = Favorites

        // эффекты (ошибки/успехи) от DevicesViewModel
        LaunchedEffect(Unit) {
            viewModel.effects.collectLatest { effect ->
                when (effect) {
                    is DevicesEffect.ShowErrorMessage ->
                        snackbarHostState.showSnackbar(effect.message)

                    is DevicesEffect.ShowSuccessMessage -> {
                        // Если нужен снекбар на успех — раскомментируй
                        // snackbarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }

        // начальная загрузка
        LaunchedEffect(Unit) {
            viewModel.process(DevicesAction.LoadInitial)
        }

        DevicesScreenContent(
            state = state,
            snackbarHostState = snackbarHostState,
            selectedRoomId = selectedRoomId,
            rooms = rooms,
            onChangeRoom = { roomId -> selectedRoomId = roomId },
            onAddRoom = { name ->
                val newRoom = RoomUi(
                    id = "room_${System.currentTimeMillis()}",
                    name = name
                )
                rooms = rooms + newRoom
                selectedRoomId = newRoom.id
            },
            onDeleteRoom = { roomId ->
                rooms = rooms.filterNot { it.id == roomId }
                if (selectedRoomId == roomId) {
                    selectedRoomId = null
                }
            },
            onAction = viewModel::process
        )
    }
}

data class RoomUi(
    val id: String,
    val name: String
)