package com.hanitacm.mymoviescmp.data.repository

import com.hanitacm.mymoviescmp.data.repository.model.MovieDataModel
import com.hanitacm.mymoviescmp.domain.model.Movie
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class MoviesRepositoryTest {
    private lateinit var moviesRepository: MoviesRepository
    private val moviesRemoteDataSource: NetworkDataSource = mock()

    @BeforeTest
    fun setUp() {
        moviesRepository = MoviesRepositoryImpl(moviesRemoteDataSource)
    }

    @Test
    fun `getPopularMovies should fetch movies from remote data source`() = runTest {
        everySuspend { moviesRemoteDataSource.getAllMovies() } returns moviesDataModel

        val result = moviesRepository.getPopularMovies()

        assertEquals(moviesDomainModel, result.first())
        verifySuspend { moviesRemoteDataSource.getAllMovies() }
    }

    private val movieDataModel =
        MovieDataModel(
            popularity = 2000.0,
            voteAverage = 0.0,
            overview =
                $$"A professional thief with $40 million in debt and his family's life on the line must commit one final heist" +
                    " - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
            posterPath = "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
            releaseDate = "2020-09-29",
            title = "Money Plane",
            originalTitle = "Money Plane",
            originalLanguage = "en",
            backdropPath = "/gYRzgYE3EOnhUkv7pcbAAsVLe5f.jpg",
            id = 694919,
        )
    private val moviesDataModel = listOf(movieDataModel)

    private val movieDomainModel =
        Movie(
            popularity = 2000.0,
            voteAverage = 0.0,
            overview =
                "A professional thief with $40 million in debt and his family's life on the line must commit one final heist" +
                    " - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
            posterPath = "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
            releaseDate = "2020-09-29",
            title = "Money Plane",
            originalTitle = "Money Plane",
            originalLanguage = "en",
            backdropPath = "/gYRzgYE3EOnhUkv7pcbAAsVLe5f.jpg",
            id = 694919,
        )
    private val moviesDomainModel = listOf(movieDomainModel)
}
