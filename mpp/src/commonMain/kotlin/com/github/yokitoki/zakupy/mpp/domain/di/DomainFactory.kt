package com.github.yokitoki.zakupy.mpp.domain.di

import com.github.aakira.napier.Napier
import com.russhwolf.settings.Settings
import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptionfactory.parser.ErrorExceptionParser
import dev.icerock.moko.network.exceptionfactory.parser.ValidationExceptionParser
import dev.icerock.moko.network.features.ExceptionFeature
import dev.icerock.moko.network.features.TokenFeature
import dev.icerock.moko.network.generated.apis.AuthApi
import dev.icerock.moko.network.generated.apis.ListApi
import io.ktor.client.HttpClient
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import com.github.yokitoki.zakupy.mpp.domain.repository.AuthRepository
import com.github.yokitoki.zakupy.mpp.domain.repository.ListRepository
import com.github.yokitoki.zakupy.mpp.domain.storage.KeyValueStorage

class DomainFactory(
    private val settings: Settings,
    private val baseUrl: String
) {
    private val keyValueStorage: KeyValueStorage by lazy { KeyValueStorage(settings) }

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    private val httpClient: HttpClient by lazy {
        HttpClient {
            install(ExceptionFeature) {
                exceptionFactory = HttpExceptionFactory(
                    defaultParser = ErrorExceptionParser(json),
                    customParsers = mapOf(
                        HttpStatusCode.UnprocessableEntity.value to ValidationExceptionParser(json)
                    )
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message = message)
                    }
                }
                level = LogLevel.HEADERS
            }
            install(TokenFeature) {
                tokenHeaderName = "Authorization"
                tokenProvider = object : TokenFeature.TokenProvider {
                    override fun getToken(): String? = keyValueStorage.token
                }
            }
            expectSuccess = false
        }
    }

    private val authApi: AuthApi by lazy {
        AuthApi(
            basePath = baseUrl,
            httpClient = httpClient,
            json = json
        )
    }

    private val listApi: ListApi by lazy {
        ListApi(
            basePath = baseUrl,
            httpClient = httpClient,
            json = json
        )
    }

    val authRepository: AuthRepository by lazy {
        AuthRepository(authApi = authApi, keyValueStorage = keyValueStorage)
    }

    val listRepository: ListRepository by lazy {
        ListRepository(listApi = listApi)
    }
}
