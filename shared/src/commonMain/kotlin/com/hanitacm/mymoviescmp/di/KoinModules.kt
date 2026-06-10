package com.hanitacm.mymoviescmp.di

import com.hanitacm.mymoviescmp.data.datasource.api.MoviesApi
import com.hanitacm.mymoviescmp.data.repository.MoviesRepository
import com.hanitacm.mymoviescmp.data.repository.MoviesRepositoryImpl
import com.hanitacm.mymoviescmp.data.repository.NetworkDataSource
import com.hanitacm.mymoviescmp.screens.MainViewModel
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
        HttpClient {
                expectSuccess = true
                install(ContentNegotiation) { json(json, contentType = ContentType.Any) }
                install(Logging) {
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                Napier.v(tag = "HTTP Client", message = message)
                            }
                        }
                    level = LogLevel.HEADERS
                }
                defaultRequest { header(HttpHeaders.Accept, ContentType.Application.Json) }
            }
            .also { Napier.base(DebugAntilog()) }
    }

    single<NetworkDataSource> { MoviesApi(get()) }

    single<MoviesRepository> { MoviesRepositoryImpl(get()) }
}

val viewModelModule = module { factory { MainViewModel(get()) } }
