package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.presentation.screen.productdetails.ProductDetailsUiState

@Composable
fun BigBrandInformation(
	modifier: Modifier,
	uiState: ProductDetailsUiState.Success,
                       ) {
	uiState.relatedBigBrandName?.let {
		Text(
			text = "Related Big Brand: $it",
			color = MaterialTheme.colorScheme.onError,
			modifier = modifier
				.background(MaterialTheme.colorScheme.error)
				.padding(16.dp),
		    )
	}
}