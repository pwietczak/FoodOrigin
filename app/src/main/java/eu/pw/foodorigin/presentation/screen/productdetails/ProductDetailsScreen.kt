package eu.pw.foodorigin.presentation.screen.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
	onBackClick: () -> Unit,
                        ) {
	val uiState = viewModel.uiState.collectAsState()

	ProductDetailsScreenContent(
		modifier = modifier,
		uiState = uiState.value,
		onBackClick = onBackClick
	                           )
}

@Composable
fun ProductDetailsScreenContent(
	modifier: Modifier,
	uiState: ProductDetailsUiState,
	onBackClick: () -> Unit,
	) {
	when (uiState) {
		is ProductDetailsUiState.Success -> SuccessScreen(
			modifier = modifier,
			uiState = uiState,
			onBackClick = onBackClick
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(
	uiState: ProductDetailsUiState.Success,
	modifier: Modifier,
	onBackClick: () -> Unit,
                 ) {
	val scrollState = rememberScrollState()
	val product = uiState.productUi

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			CenterAlignedTopAppBar(
				navigationIcon = {
					IconButton(
						onClick = onBackClick,
					          ) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = stringResource(
								R.string.back_button_content_description,
							                                   ),
						    )
					}
				},
				title = {
					Text(
						text = product.productName.orEmpty(),
						style = MaterialTheme.typography.bodyLarge,
						maxLines = 2,
						overflow = TextOverflow.Ellipsis,
					    )
				},
				windowInsets = WindowInsets(0, 0, 0, 0),
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = MaterialTheme.colorScheme.primaryContainer,
					titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
				                                          ),
			                      )
		},
		contentWindowInsets = WindowInsets(),
	        ) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
				.padding(16.dp),
		      ) {
			Column(
				modifier = Modifier
					.weight(1f)
					.verticalScroll(scrollState),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(8.dp),
			      ) {
				ProductImage(
					modifier = Modifier
						.fillMaxWidth(0.75f)
						.aspectRatio(1f)
						.padding(vertical = 16.dp),
					imageUrl = product.imageUrl,
				            )

				ProductDetailItem(
					modifier = Modifier.fillMaxWidth(),
					label = stringResource(R.string.product_details_barcode_label),
					value = product.code,
				                 )
				ProductDetailItem(
					modifier = Modifier.fillMaxWidth(),
					label = stringResource(R.string.product_details_quantity_label),
					value = product.quantity,
				                 )
				ProductDetailItem(
					modifier = Modifier.fillMaxWidth(),
					label = stringResource(R.string.product_details_brands_label),
					value = product.brand,
				                 )
				ProductLink(
					modifier = Modifier.fillMaxWidth(),
					value = product.link,
				           )
			}

			BigBrandInformation(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 8.dp),
				relatedBigBrand = uiState.relatedBigBrand,
			                   )
		}
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
			onBackClick = {}
		                           )
	}
}
