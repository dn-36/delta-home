package com.tsd_store.deltahome.data.remote.old_remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkClient {

    private var _token: String = ""

    fun init(token: String) {
        _token = token
    }

    val client = HttpClient(httpClientEngine) {
        install(ContentNegotiation) {

            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                explicitNulls = false
                encodeDefaults = false
            })
        }

        install(HttpTimeout) {

            requestTimeoutMillis = 120_000
            connectTimeoutMillis = 120_000
            socketTimeoutMillis = 120_000

        }
        install(Logging) {

         //   level = LogLevel.BODY
            level = LogLevel.HEADERS

        }
        defaultRequest {

            header("Authorization", "Bearer $_token")

        }
    }
}

val httpClientEngine: HttpClientEngine
    get() = OkHttp.create()