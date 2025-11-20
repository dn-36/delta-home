package com.tsd_store.deltahome.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import com.tsd_store.deltahome.feature.home.ui.components.HomeScreenContent
import com.tsd_store.deltahome.feature.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        HomeScreenContent(
            state = state,
            onAction = viewModel::dispatch
        )
    }
}