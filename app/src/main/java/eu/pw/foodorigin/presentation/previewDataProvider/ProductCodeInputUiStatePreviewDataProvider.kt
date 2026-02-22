package eu.pw.foodorigin.presentation.previewDataProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.foodorigin.R
import eu.pw.foodorigin.presentation.screen.productcodeinput.ProductCodeInputUiState

class ProductCodeInputUiStatePreviewDataProvider :
	PreviewParameterProvider<ProductCodeInputUiState> {
	override val values = sequenceOf(
		ProductCodeInputUiState(
			barcode = "123456789012",
			errorMessageResId = null,
			isLoading = false,
		                       ),
		ProductCodeInputUiState(
			barcode = "",
			errorMessageResId = R.string.product_code_input_error_empty_barcode,
			isLoading = false,
		                       ),
		ProductCodeInputUiState(
			barcode = "",
			errorMessageResId = R.string.product_code_input_error_empty_barcode,
			isLoading = true,
		                       ),
	                                )
}