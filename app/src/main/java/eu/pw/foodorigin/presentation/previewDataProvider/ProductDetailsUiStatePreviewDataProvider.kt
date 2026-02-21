package eu.pw.foodorigin.presentation.previewDataProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.foodorigin.R
import eu.pw.foodorigin.presentation.screen.productdetails.ProductDetailsUiState
import eu.pw.foodorigin.presentation.screen.productdetails.model.BigBrandUi
import eu.pw.foodorigin.presentation.screen.productdetails.model.ProductUi

class ProductDetailsUiStatePreviewDataProvider : PreviewParameterProvider<ProductDetailsUiState> {
	override val values = sequenceOf(
		ProductDetailsUiState.Loading,
		ProductDetailsUiState.Error(R.string.exception_unknown_error),
		ProductDetailsUiState.Success(
			productCode = "1234567890",
			productUi = ProductUi(
				id = "1234567890",
				productName = "Test Product Name",
				brand = "Test Brand",
				code = "123456789069",
				imageUrl = "image_url",
				quantity = "2137 kilo",
				link = "www.niezesrajsie.edu",
			                     ),
			relatedBigBrand = BigBrandUi(
				nameUi = "Test Big Brand",
				brandsListLink = "www.bigbrand.com",
			                            ),
		                             ),
		ProductDetailsUiState.Success(
			productCode = "1234567890",
			productUi = ProductUi(
				id = "1234567890234098230948230948203984029348209834",
				productName = "Super Long Test Product Name For Preview Or Even More Words",
				brand = "Test Brand Long Name For Preview Or Even More Words",
				code = "1234567890693452345345325345345345345345",
				imageUrl = "image_url",
				quantity = "2137546456456456 kilo",
				link = "www.niezesrajsie.edu/long_link_for_preview_need_few_more_words_so_i_am_adding_them",
			                     ),
			relatedBigBrand = BigBrandUi(
				nameUi = "Super Long Test Big Brand Name For Preview",
				brandsListLink = "www.bigbrand.com",
			                            ),
		                             ),
	                                )
}
