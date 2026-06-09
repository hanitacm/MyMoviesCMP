package com.hanitacm.mymoviescmp.data.repository.model.mapper

import com.hanitacm.mymoviescmp.data.repository.model.MovieDataModel
import com.hanitacm.mymoviescmp.domain.model.Movie

fun List<MovieDataModel>.asDomainModel() = this.map { it.asDomainModel() }

fun MovieDataModel.asDomainModel() =
    Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        popularity = popularity,
        voteAverage = voteAverage,
    )
