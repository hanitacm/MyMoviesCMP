package com.hanitacm.mymoviescmp.data.repository

import com.hanitacm.mymoviescmp.data.repository.model.MovieDataModel

interface NetworkDataSource {
    suspend fun getAllMovies(): List<MovieDataModel>
}
