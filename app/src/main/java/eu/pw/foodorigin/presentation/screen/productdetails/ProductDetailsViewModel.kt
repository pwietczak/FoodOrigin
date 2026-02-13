package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import eu.pw.foodorigin.presentation.navigation.ProductDestination
import eu.pw.foodorigin.presentation.screen.productdetails.model.toProductUi
import eu.pw.foodorigin.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductDetailsViewModel(
	savedStateHandle: SavedStateHandle,
	productRepository: ProductRepository,
                             ) : ViewModel() {

	private val productCode: String =
		savedStateHandle.toRoute<ProductDestination.ProductDetails>().productCode
	private val _uiState = MutableStateFlow(ProductDetailsUiState())
	val uiState = _uiState.asStateFlow()

	init {
		_uiState.update {
			it.copy(
				isLoading = true,
			       )
		}

		viewModelScope.launch {
			productRepository.getProduct(productCode).collect { result ->
				Timber.d("Product result: $result")
				result.onSuccess { dto ->
					_uiState.update {
						it.copy(
							productDto = dto.toProductUi(),
							productCode = productCode,
							isLoading = false,
						       )
					}
				}
				result.onFailure {
					_uiState.update {
						it.copy(
							isLoading = false,
							productCode = productCode,
						       )
					}
				}
			}
		}
	}

}