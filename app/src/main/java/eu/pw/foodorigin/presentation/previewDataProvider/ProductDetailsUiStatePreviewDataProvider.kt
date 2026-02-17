package eu.pw.foodorigin.presentation.previewDataProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.foodorigin.presentation.screen.productdetails.ProductDetailsUiState
import eu.pw.foodorigin.presentation.screen.productdetails.model.ProductUi

class ProductDetailsUiStatePreviewDataProvider : PreviewParameterProvider<ProductDetailsUiState> {
	override val values = sequenceOf(
		ProductDetailsUiState.Loading,
		ProductDetailsUiState.Error("Error"),
		ProductDetailsUiState.Success(
			productCode = "1234567890",
			productUi = ProductUi(
				id = "1234567890",
				productName = "Test Product Name",
				brand = "Test Brand",
				brandsTags = null,
				imageUrl = "image_url",
				quantity = "2137 kilo",
				link = "www.niezesrajsie.edu",
			                     ),
			relatedBigBrandName = "Test Big Brand",
		                             ),
	                                )
}
