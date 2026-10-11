package eu.pw.foodorigin.presentation.screen.productcodeinput

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.foodorigin.R
import eu.pw.foodorigin.presentation.previewDataProvider.ProductCodeInputUiStatePreviewDataProvider
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@Composable
fun ProductCodeInputScreen(
	modifier: Modifier,
	viewModel: ProductCodeInputViewModel,
	shouldFocusBarcodeInput: Boolean,
	onFocusRequestHandled: () -> Unit,
	navigateToScanCode: () -> Unit,
	onSearch: (String) -> Unit,
                          ) {

	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	LaunchedEffect(viewModel.events) {
		viewModel.events.collect { event ->
			when (event) {
				is ProductCodeUiEvent.NavigateToDetails -> onSearch(event.barcode)
			}
		}
	}

	ProductCodeInputScreenContent(
		modifier = modifier,
		uiState = uiState,
		shouldFocusBarcodeInput = shouldFocusBarcodeInput,
		onFocusRequestHandled = onFocusRequestHandled,
		onBarcodeChange = viewModel::onBarcodeChange,
		onSearchClicked = viewModel::onSearchClicked,
		navigateToScanCode = navigateToScanCode,
	                             )
}

@Composable
private fun ProductCodeInputScreenContent(
	modifier: Modifier,
	uiState: ProductCodeInputUiState,
	shouldFocusBarcodeInput: Boolean,
	onFocusRequestHandled: () -> Unit,
	onBarcodeChange: (String) -> Unit,
	onSearchClicked: () -> Unit,
	navigateToScanCode: () -> Unit,
                                         ) {
	val barcodeInputFocusRequester = remember { FocusRequester() }
	val keyboardController = LocalSoftwareKeyboardController.current

	LaunchedEffect(shouldFocusBarcodeInput) {
		if (shouldFocusBarcodeInput) {
			if (barcodeInputFocusRequester.requestFocus()) {
				keyboardController?.show()
			}
			onFocusRequestHandled()
		}
	}

	if (uiState.isLoading) {
		Box(
			modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center,
		   ) {
			CircularProgressIndicator()
		}
	}
	else Column(
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	           ) {
		Column(
			modifier = Modifier.weight(1f),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		      ) {
			Text(
				text = stringResource(R.string.product_code_input_screen_description_text),
				color = MaterialTheme.colorScheme.onBackground,
				style = MaterialTheme.typography.bodyLarge,
				textAlign = TextAlign.Center,
			    )
			Spacer(modifier = Modifier.height(16.dp))
			BarcodeTextField(
				uiState = uiState,
				onBarcodeChange = onBarcodeChange,
				navigateToScanCode = navigateToScanCode,
				onSearchClicked = onSearchClicked,
				focusRequester = barcodeInputFocusRequester,
			                )
		}
		Button(
			modifier = Modifier.fillMaxWidth(0.75f),
			onClick = onSearchClicked,
			shape = RoundedCornerShape(16.dp),
			colors = ButtonDefaults.buttonColors().copy(
				containerColor = MaterialTheme.colorScheme.primaryContainer,
				contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
			                                           ),
		      ) {
			Text(
				text = stringResource(R.string.product_code_input_screen_button_search_text),
				style = MaterialTheme.typography.bodyLarge,
				modifier = Modifier.padding(8.dp),
			    )
		}
	}
}

@Composable
private fun BarcodeTextField(
	uiState: ProductCodeInputUiState,
	onBarcodeChange: (String) -> Unit,
	navigateToScanCode: () -> Unit,
	onSearchClicked: () -> Unit,
	focusRequester: FocusRequester,
                            ) {
	OutlinedTextField(
		modifier = Modifier
			.fillMaxWidth()
			.focusRequester(focusRequester),
		value = uiState.barcode,
		onValueChange = onBarcodeChange,
		label = {
			Text(
				text = stringResource(R.string.product_code_input_screen_text_field_label),
			    )
		},
		trailingIcon = {
			IconButton(onClick = { navigateToScanCode() }) {
				Icon(
					painter = painterResource(R.drawable.barcode_scanner_24dp),
					contentDescription = stringResource(
						R.string.product_code_input_screen_button_scan_code_content_desc,
					                                   ),
				    )
			}
		},
		supportingText = {
			uiState.errorMessageResId?.let {
				Text(
					text = stringResource(it),
					color = MaterialTheme.colorScheme.error,
				    )
			}
		},
		singleLine = true,
		keyboardOptions = KeyboardOptions.Default.copy(
			keyboardType = KeyboardType.Number,
		                                              ),
		keyboardActions = KeyboardActions(
			onDone = { onSearchClicked() },
		                                 ),
	                 )
}

@PreviewLightDark
@Composable
fun ProductCodeInputScreenPreview(
	@PreviewParameter(
		ProductCodeInputUiStatePreviewDataProvider::class,
	                 ) uiState: ProductCodeInputUiState,
                                 ) {
	FoodOriginTheme {
		ProductCodeInputScreenContent(
			modifier = Modifier.background(MaterialTheme.colorScheme.background),
			uiState = uiState,
			shouldFocusBarcodeInput = false,
			onFocusRequestHandled = {},
			onBarcodeChange = {},
			onSearchClicked = {},
			navigateToScanCode = {},
		                             )
	}
}
