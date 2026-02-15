package eu.pw.foodorigin.data.repository

import app.cash.turbine.test
import eu.pw.foodorigin.data.dataSource.BigBrandDataSource
import eu.pw.foodorigin.domain.BigBrand
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class BigBrandRepositoryImplTest {
	private val dataSource = mockk<BigBrandDataSource>()
	private val repository = BigBrandRepositoryImpl(dataSource)

	private val bigBrand1 = BigBrand(
		nameUI = "test UI name",
		keywords = listOf("test UI name", "test name"),
	                                )

	@Test
	fun `getBigBrands returns data from dataSource`() = runTest {
		val bigBrands = listOf(bigBrand1)

		every { dataSource.getBigBrands() } returns flowOf(Result.success(bigBrands))

		repository.getBigBrands().test {
			val result = awaitItem()
			assert(result.isSuccess)
			assert(result.getOrNull() == bigBrands)
			awaitComplete()
		}

	}

	@Test
	fun `getBigBrands returns error when dataSource fails`() = runTest {
		val exception = Exception("Data source error")

		every { dataSource.getBigBrands() } returns flowOf(Result.failure(exception))

		repository.getBigBrands().test {
			val result = awaitItem()
			assert(result.isFailure)
			assert(result.exceptionOrNull() == exception)
			awaitComplete()
		}
	}
}

