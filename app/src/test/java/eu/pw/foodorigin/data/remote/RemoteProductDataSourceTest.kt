package eu.pw.foodorigin.data.remote

import app.cash.turbine.test
import eu.pw.foodorigin.data.remote.dto.ProductDto
import eu.pw.foodorigin.data.remote.dto.ProductResultDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RemoteProductDataSourceTest {

    private fun createMockHttpClient(jsonResponse: String, status: HttpStatusCode = HttpStatusCode.OK): HttpClient {
        val mockEngine = MockEngine { _ ->
            respond(
                content = jsonResponse,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Test
    fun `getProduct returns success when API call is successful`() = runTest {
        val expectedProduct = ProductResultDto(
            code = "123",
            status = 1,
            product = ProductDto(id = "123", productName = "Test Product",brands = "Test Brand", brandsTags = listOf("tag1", "tag2"))
        )
        val jsonResponse = Json.encodeToString(expectedProduct)
        val httpClient = createMockHttpClient(jsonResponse)
        val dataSource = RemoteProductDataSource(httpClient)

        dataSource.getProduct("123").test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            assertEquals(expectedProduct, result.getOrNull())
            awaitComplete()
        }
    }

    @Test
    fun `getProduct returns failure when API call fails`() = runTest {
        val httpClient = createMockHttpClient("Error", HttpStatusCode.InternalServerError)
        val dataSource = RemoteProductDataSource(httpClient)

        dataSource.getProduct("123").test {
            val result = awaitItem()
            assertTrue(result.isFailure)
            awaitComplete()
        }
    }

    @Test
    fun `getProduct returns failure when JSON is malformed`() = runTest {
        val httpClient = createMockHttpClient("invalid json")
        val dataSource = RemoteProductDataSource(httpClient)

        dataSource.getProduct("123").test {
            val result = awaitItem()
            assertTrue(result.isFailure)
            awaitComplete()
        }
    }

    @Test
    fun `getProduct returns nulls when JSON is missing fields `() = runTest {
        val jsonResponse = "{\"code\":\"123\",\"status\":1,\"product\":{\"id\":\"123\",\"product_name\":\"Test Product\"}}"
        val httpClient = createMockHttpClient(jsonResponse)
        val dataSource = RemoteProductDataSource(httpClient)
        val expectedProduct = ProductResultDto(
            code = "123",
            status = 1,
            product = ProductDto(id = "123", productName = "Test Product")
        )

        dataSource.getProduct("123").test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            assertEquals(expectedProduct, result.getOrNull())
            awaitComplete()
        }
    }

    //TODO add test for https respond statuses
}