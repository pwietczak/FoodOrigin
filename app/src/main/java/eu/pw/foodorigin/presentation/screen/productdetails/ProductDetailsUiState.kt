package eu.pw.foodorigin.presentation.screen.productdetails

import eu.pw.foodorigin.presentation.screen.productdetails.model.ProductUi

sealed interface ProductDetailsUiState {
	object Loading : ProductDetailsUiState
	data class Error(val errorStringRes: Int) : ProductDetailsUiState
	data class Success(
		val productCode: String = "",
		val productUi: ProductUi,
		val relatedBigBrandName: String? = null,
	                  ) : ProductDetailsUiState
}
