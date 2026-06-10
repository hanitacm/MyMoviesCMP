package com.hanitacm.mymoviescmp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanitacm.mymoviescmp.screens.MainScreen
import com.hanitacm.mymoviescmp.screens.MainUiState
import com.hanitacm.mymoviescmp.screens.MainViewModel
import com.hanitacm.mymoviescmp.ui.theme.MyMoviesCMPTheme
import com.hanitacm.mymoviescmp.ui.theme.ui.composables.TopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MyMoviesCMPTheme {
        val snackBarHostState = remember { SnackbarHostState() }
        Scaffold(
            topBar = { TopBar() },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        ) { paddingValues ->
            val viewModel: MainViewModel = koinViewModel()
            val uiState: MainUiState by viewModel.viewState.collectAsStateWithLifecycle()
            MainScreen(
                uiState = uiState,
                onRefresh = viewModel::refresh,
                onMovieClick = {},
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}
