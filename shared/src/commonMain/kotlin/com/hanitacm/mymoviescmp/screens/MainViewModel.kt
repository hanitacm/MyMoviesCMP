package com.hanitacm.mymoviescmp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanitacm.mymoviescmp.data.repository.MoviesRepository
import com.hanitacm.mymoviescmp.domain.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(moviesRepository: MoviesRepository) : ViewModel() {
    private val refreshSignal = MutableStateFlow(0)
    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState: StateFlow<MainUiState> =
        refreshSignal
            .flatMapLatest {
                moviesRepository
                    .getPopularMovies()
                    .map { MainUiState.Success(isLoading = false, it) as MainUiState }
                    .onStart { emit(MainUiState.Success(true, emptyList())) }
                    .catch { emit(MainUiState.Failure(it)) }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainUiState.Success(true, emptyList()),
            )

    fun refresh() {
        refreshSignal.update { it + 1 }
    }
}

sealed class MainUiState {
    data class Success(val isLoading: Boolean, val movies: List<Movie>) : MainUiState()

    data class Failure(val error: Throwable) : MainUiState()
}
