package eu.pw.foodorigin.presentation.screen.productcodeinput

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import eu.pw.foodorigin.R
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@Composable
fun ProductCodeInputScreen(
	modifier: Modifier,
	viewModel: ProductCodeInputViewModel,
	navigateToScanCode: () -> Unit,
	onSearch: (String) -> Unit,
                          ) {

	ProductCodeInputScreenContent(
		modifier = modifier,
		navigateToScanCode = navigateToScanCode,
		onSearch = onSearch
	                             )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ProductCodeInputScreenContent(
	modifier: Modifier,
	navigateToScanCode: () -> Unit,
	onSearch: (String) -> Unit,
	) {
	var barcode by rememberSaveable { mutableStateOf("") }

	Column (
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
				modifier = Modifier.padding(16.dp),
				textAlign = TextAlign.Center,
			    )
			Spacer(modifier = Modifier.height(16.dp))
			OutlinedTextField(
				value = barcode,
				onValueChange = { barcode = it },
				label = { Text(stringResource(R.string.product_code_input_screen_text_field_label)) },
				trailingIcon = {
					IconButton(onClick = { navigateToScanCode() }) {
						Icon(
							imageVector = Icons.Default.QrCodeScanner,
							contentDescription = stringResource(
								R.string.product_code_input_screen_button_scan_code_content_desc,
							                                   ),
						    )
					}
				},
				singleLine = true,
				keyboardOptions = KeyboardOptions.Default.copy(
					keyboardType = KeyboardType.Number,
				                                              ),
				keyboardActions = KeyboardActions(
					onDone = { onSearch(barcode) }
				),
			                 )

		}
		Button(onClick = {onSearch(barcode)}) {
			Text(
				text = stringResource(R.string.product_code_input_screen_button_search_text),
			    )
		}
	}
}

@PreviewLightDark
@Composable
fun ProductCodeInputScreenPreview() {
	FoodOriginTheme {
		ProductCodeInputScreenContent(
			modifier = Modifier.background(MaterialTheme.colorScheme.background),
			navigateToScanCode = {},
			onSearch = {},
		                             )
	}
}