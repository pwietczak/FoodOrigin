package eu.pw.foodorigin.presentation.screen.productcodeinput

data class ProductCodeInputUiState(
	val barcode: String = "",
	val errorMessageResId: Int? = null,
	val isLoading: Boolean = false,
                                  )