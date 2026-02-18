package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import eu.pw.foodorigin.R
import eu.pw.foodorigin.domain.BigBrand
import eu.pw.foodorigin.domain.LocalizedException
import eu.pw.foodorigin.domain.Product
import eu.pw.foodorigin.domain.repository.BigBrandRepository
import eu.pw.foodorigin.domain.repository.ProductRepository
import eu.pw.foodorigin.domain.useCase.GetRelatedBigBrand
import eu.pw.foodorigin.presentation.navigation.ProductDestination
import eu.pw.foodorigin.presentation.screen.productdetails.model.toProductUi
import eu.pw.foodorigin.presentation.screen.productdetails.model.toUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class ProductDetailsViewModel(
	savedStateHandle: SavedStateHandle,
	productRepository: ProductRepository,
	bigBrandRepository: BigBrandRepository,
	private val getRelatedBigBrand: GetRelatedBigBrand,
                             ) : ViewModel() {

	private val productCode: String =
		savedStateHandle.toRoute<ProductDestination.ProductDetails>().productCode
	val uiState: StateFlow<ProductDetailsUiState> = combine(
		productRepository.getProduct(productCode),
		bigBrandRepository.getBigBrands(),
	                                                       ) { productResult, bigBrandsResult ->
		createUiState(productResult, bigBrandsResult)
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5_000),
		initialValue = ProductDetailsUiState.Loading,
	         )

	private fun createUiState(
		productResult: Result<Product>,
		bigBrandsResult: Result<List<BigBrand>>,
	                         ): ProductDetailsUiState {
		return try {
			val product = productResult.getOrThrow()
			val bigBrands = bigBrandsResult.getOrThrow()

			Timber.d("Product: $product")
			Timber.d("BigBrands: $bigBrands")

			// TODO need to thin about what exactly happens here
			val relatedBigBrandName = getRelatedBigBrand(product, bigBrands)
			ProductDetailsUiState.Success(
				productUi = product.toProductUi(),
				productCode = productCode,
				relatedBigBrand = relatedBigBrandName?.toUi(),
			                             )
		}
		catch (
			le: LocalizedException, ) {
			Timber.e(le)
			ProductDetailsUiState.Error(le.messageResId)
		}
		catch (
			e: Exception, ) {
			Timber.e(e)
			ProductDetailsUiState.Error(R.string.exception_unknown_error)
		}
	}

}
