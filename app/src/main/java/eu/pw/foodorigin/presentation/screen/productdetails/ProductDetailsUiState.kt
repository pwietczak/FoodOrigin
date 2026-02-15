package eu.pw.foodorigin.presentation.screen.productdetails

import eu.pw.foodorigin.presentation.screen.productdetails.model.ProductUi

data class ProductDetailsUiState(
	val isLoading : Boolean = true,
	val productCode : String = "",
	val productDto: ProductUi? = null,
	val relatedBigBrandName: String? = null
								)