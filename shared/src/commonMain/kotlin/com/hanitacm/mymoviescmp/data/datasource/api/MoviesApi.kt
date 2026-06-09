package com.hanitacm.mymoviescmp.data.datasource.api

import com.hanitacm.mymoviescmp.data.datasource.api.model.GetMoviesApiResponse
import com.hanitacm.mymoviescmp.data.datasource.api.model.mapper.asDataModel
import com.hanitacm.mymoviescmp.data.repository.NetworkDataSource
import com.hanitacm.mymoviescmp.data.repository.model.MovieDataModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MoviesApi(private val client: HttpClient) : NetworkDataSource {
    companion object {
        private const val API_URL: String = "https://api.themoviedb.org/3/"
        private const val API_KEY = "4b2dda035db530ab9de5426133354f16"
    }

    override suspend fun getAllMovies(): List<MovieDataModel> {
        return try {
            client
                .get("${API_URL}discover/movie?sort_by=popularity.desc&api_key=$API_KEY")
                .body<GetMoviesApiResponse>()
                .asDataModel()
        } catch (ex: Exception) {
            emptyList()
        }
    }
}
