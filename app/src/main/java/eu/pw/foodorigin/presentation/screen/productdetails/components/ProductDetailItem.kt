package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@Composable
fun ProductDetailItem(
	modifier: Modifier,
	label: String,
	value: String?,
                     ) {
	if (!value.isNullOrBlank()) {
		LabeledItem(
			modifier = modifier,
			label = label,
			backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
			onClick = null,
		           ) {
				Text(
					text = value,
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
				    )
			}
		}
	}


@PreviewLightDark
@Composable
fun ProductDetailItemPreview() {
	FoodOriginTheme {
		ProductDetailItem(
			modifier = Modifier
				.background(MaterialTheme.colorScheme.background)
				.padding(16.dp),
			label = "Label",
			value = "Value",
		                 )
	}
}
