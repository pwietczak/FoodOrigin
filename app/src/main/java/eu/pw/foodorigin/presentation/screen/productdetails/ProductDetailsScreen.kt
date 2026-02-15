package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.presentation.screen.productdetails.model.ProductUi
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@Composable
fun ProductDetailsScreen(
	modifier: Modifier,
	viewModel: ProductDetailsViewModel,
                        ) {
	val uiState = viewModel.uiState.collectAsState()

	ProductDetailsScreenContent(
		modifier = modifier,
		uiState = uiState.value,
	                           )
}

@Composable
fun ProductDetailsScreenContent(
	modifier: Modifier,
	uiState: ProductDetailsUiState,
                               ) {
	if (uiState.isLoading)
		Box(
			modifier = modifier
				.fillMaxSize()
				.padding(16.dp),
			contentAlignment = Alignment.Center,
	                          ) {
		CircularProgressIndicator()
	}
	else {
		Column(
			modifier = modifier
				.fillMaxSize()
				.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		      ) {
			Text(
				text = "Product Details",
				color = MaterialTheme.colorScheme.onBackground,
				style = MaterialTheme.typography.headlineLarge,
				textAlign = TextAlign.Center,
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp),
			    )
			Column(
				modifier = Modifier.weight(1f),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center,
			      ) {
				Text(
					text = "Product Code: ${uiState.productCode}",
					color = MaterialTheme.colorScheme.onBackground,
				    )
				Text(
					text = uiState.productDto.toString()
						.replace(",", ",\n")
						.replace("(", "\n(\n")
						.replace(")", "\n)\n"),
					color = MaterialTheme.colorScheme.onBackground,
				    )
			}
			uiState.relatedBigBrandName?.let {
				Text(
					text = "Related Big Brand: $it",
					color = MaterialTheme.colorScheme.onError,
					modifier = Modifier
						.background(MaterialTheme.colorScheme.error)
						.padding(16.dp),
				    )
			}
		}
	}
}

@PreviewLightDark
@Composable
fun ProductDetailsScreenPreview() {
	FoodOriginTheme {
		ProductDetailsScreenContent(
			modifier = Modifier.background(MaterialTheme.colorScheme.background),
			uiState = ProductDetailsUiState(
				isLoading = false,
				productCode = "1234567890",
				productDto = ProductUi(
					id = "1234567890",
					productName = "Test Product",
					brand = "Test Brand",
					brandsTags = null,
				                      ),
				relatedBigBrandName = "Test Big Brand",
			                               ),
		                           )
	}
}