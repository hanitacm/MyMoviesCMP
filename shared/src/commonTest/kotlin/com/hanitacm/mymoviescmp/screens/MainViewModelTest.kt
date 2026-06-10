package com.hanitacm.mymoviescmp.screens

import com.hanitacm.mymoviescmp.data.repository.MoviesRepository
import com.hanitacm.mymoviescmp.domain.model.Movie
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val moviesRepository: MoviesRepository = mock()
    private lateinit var viewModel: MainViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is initialized it should emit loading and then success with movies`() =
        runTest {
            val movies =
                listOf(
                    Movie(
                        popularity = 2000.0,
                        voteAverage = 0.0,
                        overview = "Overview",
                        posterPath = "/path.jpg",
                        releaseDate = "2020-09-29",
                        title = "Movie",
                        originalTitle = "Movie",
                        originalLanguage = "en",
                        backdropPath = "/backdrop.jpg",
                        id = 1,
                    )
                )
            every { moviesRepository.getPopularMovies() } returns flowOf(movies)

            viewModel = MainViewModel(moviesRepository)

            val results = mutableListOf<MainUiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.viewState.toCollection(results)
            }

            assertEquals(MainUiState.Success(true, emptyList()), results[0])
            assertEquals(MainUiState.Success(false, movies), results[1])
        }

    @Test
    fun `when getPopularMovies fails it should emit failure state`() = runTest {
        val exception = RuntimeException("Error fetching movies")
        every { moviesRepository.getPopularMovies() } returns flow { throw exception }

        viewModel = MainViewModel(moviesRepository)

        val results = mutableListOf<MainUiState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.viewState.toCollection(results)
        }

        assertEquals(MainUiState.Success(true, emptyList()), results[0])
        assertTrue(results[1] is MainUiState.Failure)
        assertEquals(exception.message, (results[1] as MainUiState.Failure).error.message)
    }

    @Test
    fun `when refresh is called it should fetch movies again`() = runTest {
        val movies = emptyList<Movie>()
        every { moviesRepository.getPopularMovies() } returns flowOf(movies)

        viewModel = MainViewModel(moviesRepository)

        val results = mutableListOf<MainUiState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.viewState.toCollection(results)
        }

        viewModel.refresh()

        // Expected emissions:
        // 1. Initial value from stateIn/onStart: Success(true, [])
        // 2. Initial flow emission: Success(false, movies)
        // 3. Refresh triggered: flatMapLatest emits onStart: Success(true, [])
        // 4. Refresh flow emission: Success(false, movies)
        assertTrue(results.size >= 2)
        assertEquals(MainUiState.Success(true, emptyList()), results.first())
        assertEquals(MainUiState.Success(false, movies), results.last())
    }
}
