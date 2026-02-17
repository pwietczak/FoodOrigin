package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.R
import eu.pw.foodorigin.presentation.previewDataProvider.ProductDetailsUiStatePreviewDataProvider
import eu.pw.foodorigin.presentation.screen.productdetails.components.BigBrandInformation
import eu.pw.foodorigin.presentation.screen.productdetails.components.ProductDetailItem
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
	val scrollState = rememberScrollState()
	Column(
		modifier = modifier.fillMaxSize()
		.padding(16.dp),
	) {
	Column(
		modifier = Modifier
			.weight(1f)
			.verticalScroll(scrollState),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		Text(
			text = uiState.productUi.productName ?: "",
			color = MaterialTheme.colorScheme.onSurface,
			style = MaterialTheme.typography.headlineMedium,
			maxLines = 3,
			overflow = TextOverflow.Ellipsis,
			textAlign = TextAlign.Center,
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp),
		)
		ProductImage(
			modifier = Modifier.size(240.dp),
			imageUrl = uiState.productUi.imageUrl,
		)
		ProductDetailItem(
			modifier = Modifier.fillMaxWidth(),
			label = stringResource(R.string.product_details_barcode_label),
			value = uiState.productCode,
		)
		ProductDetailItem(
			modifier = Modifier.fillMaxWidth(),
			label = stringResource(R.string.product_details_quantity_label),
			value = uiState.productUi.quantity,
		)
		ProductDetailItem(
			modifier = Modifier.fillMaxWidth(),
			label = stringResource(R.string.product_details_brands_label),
			value = uiState.productUi.brand,
		)
		ProductLink(
			modifier = Modifier.fillMaxWidth(),
			value = uiState.productUi.link,
		)
	}
		BigBrandInformation(
			modifier = Modifier.fillMaxWidth(),
			uiState = uiState,
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
			text = stringResource(uiState.errorStringRes),
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
