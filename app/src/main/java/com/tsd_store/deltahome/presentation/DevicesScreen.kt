package com.tsd_store.deltahome.presentation

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import com.tsd_store.deltahome.presentation.components.DevicesScreenContent
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.mp.KoinPlatform.getKoin

class DevicesScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: DevicesViewModel = koinViewModel()

        val state by viewModel.state.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(Unit) {
            viewModel.effects.collectLatest { effect ->
                when (effect) {
                    is DevicesEffect.ShowErrorMessage ->
                        snackbarHostState.showSnackbar(effect.message)

                    is DevicesEffect.ShowSuccessMessage -> {
                        // snackbarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.process(DevicesAction.LoadInitial)
        }

        DevicesScreenContent(
            state = state,
            snackbarHostState = snackbarHostState,
            onAction = viewModel::process
        )
    }
}