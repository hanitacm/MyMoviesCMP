package com.hanitacm.mymoviescmp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hanitacm.mymoviescmp.domain.model.Movie
import com.hanitacm.mymoviescmp.ui.theme.MyMoviesCMPTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    uiState: MainUiState,
    onRefresh: () -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val pullRefreshState = rememberPullToRefreshState()

    when (uiState) {
        is MainUiState.Failure -> {
            uiState.error.message?.let {
                coroutineScope.launch {
                    // snackBarHostState.showSnackbar(message = it)
                }
            }
        }

        is MainUiState.Success -> {
            PullToRefreshBox(
                contentAlignment = Alignment.TopCenter,
                isRefreshing = uiState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.fillMaxSize(),
                onRefresh = onRefresh,
            ) {
                MovieList(movies = uiState.movies, onMovieClick = onMovieClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenLoadingPreview() {
    MyMoviesCMPTheme {
        MainScreen(
            uiState = MainUiState.Success(isLoading = true, movies = emptyList()),
            onRefresh = {},
            onMovieClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    val movies =
        List(10) {
            Movie(
                popularity = 2000.0,
                voteAverage = 0.0,
                overview = "Overview $it",
                posterPath = "",
                releaseDate = "2020-09-29",
                title = "Movie $it",
                originalTitle = "Movie $it",
                originalLanguage = "en",
                backdropPath = "",
                id = it,
            )
        }
    MyMoviesCMPTheme {
        MainScreen(
            uiState = MainUiState.Success(isLoading = false, movies = movies),
            onRefresh = {},
            onMovieClick = {},
        )
    }
}
