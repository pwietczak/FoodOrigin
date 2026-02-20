package eu.pw.foodorigin.data.remote.util

import eu.pw.foodorigin.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.userAgent
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
	fun create(
		engine: HttpClientEngine,
		userAgentProvider: UserAgentProvider,
	          ): HttpClient = HttpClient(engine) {
		install(Logging) {
			if (BuildConfig.DEBUG) {
				logger = Logger.ANDROID
				level = LogLevel.ALL
			}
			else {
				logger = Logger.EMPTY
				level = LogLevel.NONE
			}
		}
		install(ContentNegotiation) {
			json(
				json = Json {
					ignoreUnknownKeys = true
				},
			    )
		}
		defaultRequest {
			contentType(ContentType.Application.Json)
			userAgent(userAgentProvider())
		}
	}
}