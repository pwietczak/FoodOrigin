package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import eu.pw.foodorigin.R
import eu.pw.foodorigin.domain.BigBrand
import eu.pw.foodorigin.domain.Product
import eu.pw.foodorigin.domain.repository.BigBrandRepository
import eu.pw.foodorigin.domain.repository.ProductRepository
import eu.pw.foodorigin.domain.useCase.GetRelatedBigBrand
import eu.pw.foodorigin.presentation.navigation.ProductDestination
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductDetailsViewModelTest {

	private val testDispatcher = StandardTestDispatcher()

	private lateinit var productRepository: ProductRepository
	private lateinit var bigBrandRepository: BigBrandRepository
	private lateinit var getRelatedBigBrand: GetRelatedBigBrand
	private lateinit var savedStateHandle: SavedStateHandle
	private lateinit var viewModel: ProductDetailsViewModel

	private val productCode = "123456789"

	@Before
	fun setUp() {
		Dispatchers.setMain(testDispatcher)

		productRepository = mockk()
		bigBrandRepository = mockk()
		getRelatedBigBrand = mockk()
		savedStateHandle = mockk()

		mockkStatic("androidx.navigation.SavedStateHandleKt")
		every {
			savedStateHandle.toRoute<ProductDestination.ProductDetails>()
		} returns ProductDestination.ProductDetails(productCode = productCode)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	private fun createViewModel() {
		viewModel = ProductDetailsViewModel(
			savedStateHandle = savedStateHandle,
			productRepository = productRepository,
			bigBrandRepository = bigBrandRepository,
			getRelatedBigBrand = getRelatedBigBrand,
		                                   )
	}

	@Test
	fun `uiState should start with Loading`() = runTest {
		coEvery { productRepository.getProduct(any()) } returns flowOf()
		coEvery { bigBrandRepository.getBigBrands() } returns flowOf()

		createViewModel()

		assertEquals(ProductDetailsUiState.Loading, viewModel.uiState.value)
	}

	@Test
	fun `uiState should be Success when repositories return success`() = runTest {
		val product = Product(
			productName = "Test Product", code = productCode, brands = "Test Brand",
			id = "id",
			brandsTags = listOf("tag1", "tag2"),
			imageUrl = "",
			quantity = "",
			link = "",
		                     )
		val bigBrands = listOf(BigBrand(nameUI = "Test Big Brand", brandsListLink = "Test Brand", keywords = emptyList()))
		val relatedBigBrand = bigBrands.first()

		coEvery { productRepository.getProduct(productCode) } returns flowOf(Result.success(product))
		coEvery { bigBrandRepository.getBigBrands() } returns flowOf(Result.success(bigBrands))
		every { getRelatedBigBrand(product, bigBrands) } returns relatedBigBrand

		createViewModel()

		viewModel.uiState.test {
			assertEquals(ProductDetailsUiState.Loading, awaitItem())
			val successState = awaitItem() as ProductDetailsUiState.Success
			assertEquals(product.productName, successState.productUi.productName)
			assertEquals(relatedBigBrand.nameUI, successState.relatedBigBrand?.nameUi)
			cancelAndConsumeRemainingEvents()
		}
	}

	@Test
	fun `uiState should be Error when productRepository returns failure`() = runTest {
		val exception = Exception("Product fetch failed")
		coEvery { productRepository.getProduct(productCode) } returns flowOf(Result.failure(exception))
		coEvery { bigBrandRepository.getBigBrands() } returns flowOf(Result.success(emptyList()))

		createViewModel()

		viewModel.uiState.test {
			assertEquals(ProductDetailsUiState.Loading, awaitItem())
			val errorState = awaitItem() as ProductDetailsUiState.Error
			assertEquals(R.string.exception_unknown_error, errorState.errorStringRes)
			cancelAndConsumeRemainingEvents()
		}
	}

	@Test
	fun `uiState should be Error when bigBrandRepository returns failure`() = runTest {
		val product = Product(
			productName = "Test Product", code = productCode, brands = "Test Brand",
			id = "id",
			brandsTags = listOf("tag1", "tag2"),
			imageUrl = "",
			quantity = "",
			link = "",
		                     )
		val exception = Exception("Big brands fetch failed")
		coEvery { productRepository.getProduct(productCode) } returns flowOf(Result.success(product))
		coEvery { bigBrandRepository.getBigBrands() } returns flowOf(Result.failure(exception))

		createViewModel()

		viewModel.uiState.test {
			assertEquals(ProductDetailsUiState.Loading, awaitItem())
			val errorState = awaitItem() as ProductDetailsUiState.Error
			assertEquals(R.string.exception_unknown_error, errorState.errorStringRes)
			cancelAndConsumeRemainingEvents()
		}
	}


}