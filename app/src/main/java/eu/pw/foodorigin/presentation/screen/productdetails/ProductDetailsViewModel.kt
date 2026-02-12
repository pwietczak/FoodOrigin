package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import eu.pw.foodorigin.presentation.navigation.ProductDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductDetailsViewModel(
	savedStateHandle: SavedStateHandle,
                             ) : ViewModel() {

	private val productCode: String =
		savedStateHandle.toRoute<ProductDestination.ProductDetails>().productCode
	private val _uiState = MutableStateFlow(ProductDetailsUiState())
	val uiState = _uiState.asStateFlow()

	init {
		_uiState.update {
			it.copy(
				productCode = productCode,
				isLoading = false,
			       )
		}
	}

}