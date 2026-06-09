package com.hanitacm.mymoviescmp.data.datasource.api.model.mapper

import com.hanitacm.mymoviescmp.data.datasource.api.model.GetMoviesApiResponse
import com.hanitacm.mymoviescmp.data.repository.model.MovieDataModel

fun GetMoviesApiResponse.asDataModel(): List<MovieDataModel> = results.map {
    MovieDataModel(
        id = it.id,
        title = it.title,
        overview = it.overview,
        releaseDate = it.releaseDate.orEmpty(),
        posterPath = it.posterPath.orEmpty(),
        backdropPath = it.backdropPath.orEmpty(),
        originalLanguage = it.originalLanguage,
        originalTitle = it.originalTitle,
        popularity = it.popularity,
        voteAverage = it.voteAverage,
    )
}
