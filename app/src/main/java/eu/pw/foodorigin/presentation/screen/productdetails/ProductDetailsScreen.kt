package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.R
import eu.pw.foodorigin.presentation.previewDataProvider.ProductDetailsUiStatePreviewDataProvider
import eu.pw.foodorigin.presentation.screen.productdetails.components.BigBrandInformation
import eu.pw.foodorigin.presentation.screen.productdetails.components.ProductImage
import eu.pw.foodorigin.presentation.screen.productdetails.components.ProductLink
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
	when (uiState) {
		is ProductDetailsUiState.Success -> SuccessScreen(
			modifier = modifier,
			uiState = uiState,
		                                                 )

		is ProductDetailsUiState.Loading -> LoadingScreen(
			modifier = modifier,
		                                                 )

		is ProductDetailsUiState.Error -> ErrorScreen(
			modifier = modifier,
			uiState = uiState,
		                                             )
	}
}

@Composable
fun SuccessScreen(
	modifier: Modifier,
	uiState: ProductDetailsUiState.Success,
                 ) {
	Column(
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(
			space = 8.dp,
			alignment = Alignment.CenterVertically,
		                                          ),
	      ) {
		Text(
			text = uiState.productUi.productName ?: "",
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.headlineLarge,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			textAlign = TextAlign.Center,
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
		    )
		Row(
			modifier = Modifier.fillMaxWidth(1f),
			horizontalArrangement = Arrangement.Start,
			verticalAlignment = Alignment.CenterVertically,
		   ) {
			Icon(
				painter = painterResource(R.drawable.barcode_24dp),
				contentDescription = null, //TODO add description
				modifier = Modifier.size(24.dp),
				tint = MaterialTheme.colorScheme.onBackground,
			    )
			DetailRow(
				modifier = Modifier.weight(1f),
				label = "",
				value = uiState.productCode,
			         )
		}
		ProductImage(
			modifier = modifier.size(200.dp), //TODO need some nice modifier for this bad boy
			imageUrl = uiState.productUi.imageUrl,
		            )
		Column(
			modifier = Modifier.weight(1f),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(
				space = 8.dp,
				alignment = Alignment.CenterVertically,
			                                          ),
		      ) {
			DetailRow(
				modifier = Modifier.fillMaxWidth(1f),
				label = "Quantity",
				value = uiState.productUi.quantity,
			         )
			DetailRow(
				modifier = Modifier.fillMaxWidth(1f),
				label = "Brands",
				value = uiState.productUi.brand,
			         )
			ProductLink(
				modifier = Modifier.fillMaxWidth(1f),
				value = uiState.productUi.link,
			           )
		}
		BigBrandInformation(
			modifier = Modifier.fillMaxWidth(1f),
			uiState = uiState,
		                   )
	}
}

@Composable
fun DetailRow(
	modifier: Modifier,
	label: String,
	value: String?,
             ) {
	if (!value.isNullOrBlank()) {
		Text(
			text = "$label: $value",
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.bodyLarge,
			modifier = modifier,
			textAlign = TextAlign.Start,
		    )
	}
}

@Composable
fun LoadingScreen(
	modifier: Modifier,
                 ) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp),
		contentAlignment = Alignment.Center,
	   ) {
		CircularProgressIndicator()
	}
}

@Composable
fun ErrorScreen(
	modifier: Modifier,
	uiState: ProductDetailsUiState.Error,
               ) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp),
		contentAlignment = Alignment.Center,
	   ) {
		Text(
			text = uiState.errorMessage,
			color = MaterialTheme.colorScheme.onBackground,
		    )
	}
}

@PreviewLightDark
@Composable
fun ProductDetailsScreenPreview(
	@PreviewParameter(
		ProductDetailsUiStatePreviewDataProvider::class,
	                 ) uiState: ProductDetailsUiState,
                               ) {
	FoodOriginTheme {
		ProductDetailsScreenContent(
			modifier = Modifier.background(MaterialTheme.colorScheme.background),
			uiState = uiState,
		                           )
	}
}