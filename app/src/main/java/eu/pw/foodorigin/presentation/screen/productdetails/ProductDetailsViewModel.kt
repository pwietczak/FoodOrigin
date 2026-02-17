package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import eu.pw.foodorigin.domain.repository.BigBrandRepository
import eu.pw.foodorigin.domain.repository.ProductRepository
import eu.pw.foodorigin.domain.useCase.GetRelatedBigBrand
import eu.pw.foodorigin.presentation.navigation.ProductDestination
import eu.pw.foodorigin.presentation.screen.productdetails.model.toProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductDetailsViewModel(
	savedStateHandle: SavedStateHandle,
	productRepository: ProductRepository,
	bigBrandRepository: BigBrandRepository,
	getRelatedBigBrand: GetRelatedBigBrand,
                             ) : ViewModel() {

	private val productCode: String =
		savedStateHandle.toRoute<ProductDestination.ProductDetails>().productCode
	private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
	val uiState = _uiState.asStateFlow()

	init {
		viewModelScope.launch {
			val productFlow = productRepository.getProduct(productCode)
			val bigBrandsFlow = bigBrandRepository.getBigBrands()

			combine(productFlow, bigBrandsFlow) { productResult, bigBrandsResult ->
				if (productResult.isSuccess && bigBrandsResult.isSuccess) {
					val product = productResult.getOrThrow()
					val bigBrands = bigBrandsResult.getOrThrow()

					Timber.d("Product: $product")
					Timber.d("BigBrands: $bigBrands")

					val relatedBigBrandName = getRelatedBigBrand(product, bigBrands)?.nameUI
					_uiState.value = ProductDetailsUiState.Success(
						productUi = product.toProductUi(),
						productCode = productCode,
						relatedBigBrandName = relatedBigBrandName,
					                                              )
				}
				else {
					_uiState.value = ProductDetailsUiState.Error(productCode)
				}
			}.collect()
		}
	}
}