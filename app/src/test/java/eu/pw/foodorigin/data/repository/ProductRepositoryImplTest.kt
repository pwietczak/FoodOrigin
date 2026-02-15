package eu.pw.foodorigin.data.repository

import app.cash.turbine.test
import eu.pw.foodorigin.R
import eu.pw.foodorigin.data.dataSource.ProductDataSource
import eu.pw.foodorigin.data.mapper.ProductResultDtoMapper
import eu.pw.foodorigin.data.remote.dto.ProductDto
import eu.pw.foodorigin.data.remote.dto.ProductResultDto
import eu.pw.foodorigin.domain.LocalizedException
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductRepositoryImplTest {

    private val dataSource = mockk<ProductDataSource>()
    private val mapper = ProductResultDtoMapper()
    private val repository = ProductRepositoryImpl(dataSource, mapper)

    @Test
    fun `getProduct returns success when status is 1 and product is not null`() = runTest {
        val code = "123456"
        val productDto = ProductDto(id = "1", productName = "Test Product")
        val resultDto = ProductResultDto(code = code, status = 1, product = productDto)
        
        every { dataSource.getProduct(code) } returns flowOf(Result.success(resultDto))

        repository.getProduct(code).test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            val product = result.getOrNull()
            assertEquals("Test Product", product?.productName)
            assertEquals(code, product?.code)
            awaitComplete()
        }
    }

    @Test
    fun `getProduct returns failure when status is not 1`() = runTest {
        val code = "123456"
        val resultDto = ProductResultDto(code = code, status = 0, product = null)
        
        every { dataSource.getProduct(code) } returns flowOf(Result.success(resultDto))

        repository.getProduct(code).test {
            val result = awaitItem()
            assertTrue(result.isFailure)
            assertEquals(LocalizedException(R.string.exception_product_repository_product_not_found),result.exceptionOrNull())
            awaitComplete()
        }
    }

    @Test
    fun `getProduct returns failure when product is null`() = runTest {
        val code = "123456"
        val resultDto = ProductResultDto(code = code, status = 1, product = null)
        
        every { dataSource.getProduct(code) } returns flowOf(Result.success(resultDto))

        repository.getProduct(code).test {
            val result = awaitItem()
            assertTrue(result.isFailure)
            assertEquals(LocalizedException(R.string.exception_product_repository_product_not_found),result.exceptionOrNull())
            awaitComplete()
        }
    }

    @Test
    fun `getProduct returns failure when data source returns failure`() = runTest {
        val code = "123456"
        val exception = Exception("Network error")
        
        every { dataSource.getProduct(code) } returns flowOf(Result.failure(exception))

        repository.getProduct(code).test {
            val result = awaitItem()
            assertTrue(result.isFailure)
            assertEquals("Network error", result.exceptionOrNull()?.message)
            awaitComplete()
        }
    }
}
