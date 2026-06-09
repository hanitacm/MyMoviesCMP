package com.hanitacm.mymoviescmp.data.repository

import com.hanitacm.mymoviescmp.data.repository.model.mapper.asDomainModel
import com.hanitacm.mymoviescmp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MoviesRepository {
    fun getPopularMovies(): Flow<List<Movie>>
}

class MoviesRepositoryImpl(private val moviesApi: NetworkDataSource) : MoviesRepository {
    override fun getPopularMovies(): Flow<List<Movie>> = flow {
        emit(moviesApi.getAllMovies().asDomainModel().sortedByDescending { it.voteAverage })
    }

    // suspend fun getMovieDetail(id: Int) = moviesCache.getMovieDetail(id).asDomainModel()
}
