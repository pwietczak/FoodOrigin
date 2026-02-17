package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@Composable
fun ProductDetailItem(
	modifier: Modifier,
	label: String,
	value: String?,
	iconPainter: Painter? = null,
) {
	if (!value.isNullOrBlank()) {
		Column(
			modifier = modifier
		) {
			Text(
				text = label,
				style = MaterialTheme.typography.labelLarge,
				color = MaterialTheme.colorScheme.outline,
			)
			Spacer(modifier = Modifier.height(2.dp))
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.clip(RoundedCornerShape(12.dp))
					.background(MaterialTheme.colorScheme.surfaceVariant)
					.padding(horizontal = 16.dp, vertical = 12.dp),
				verticalAlignment = Alignment.CenterVertically,
			) {
				iconPainter?.let {
					Icon(
						painter = iconPainter,
						contentDescription = null,
						tint = MaterialTheme.colorScheme.primary,
						modifier = Modifier
							.padding(end = 12.dp)
							.size(20.dp),
					)
				}
				Text(
					text = value,
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
				)
			}
		}
	}
}

@PreviewLightDark
@Composable
fun ProductDetailItemPreview(){
	FoodOriginTheme{
		ProductDetailItem(
			modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp),
			label = "Label",
			value = "Value",
		)
	}
}
