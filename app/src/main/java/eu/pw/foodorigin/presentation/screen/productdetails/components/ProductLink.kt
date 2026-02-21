package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.R
import eu.pw.foodorigin.data.remote.util.normalizeUrl
import eu.pw.foodorigin.ui.theme.FoodOriginTheme
import timber.log.Timber

@Composable
fun ProductLink(
	modifier: Modifier,
	value: String?,
               ) {
	val urlToOpen = normalizeUrl(value)
	val uriHandler = LocalUriHandler.current

	if (urlToOpen != null && value != null) {
		LabeledItem(
			modifier = modifier,
			label = stringResource(R.string.product_details_link_label),
			backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
			onClick = {
				try {
					uriHandler.openUri(urlToOpen)
				}
				catch (e: Exception) {
					Timber.e(e, "Could not open URI: $urlToOpen")
				}
			},
		           ) {
			Text(
				text = value,
				style = MaterialTheme.typography.bodyMedium.copy(
					fontWeight = FontWeight.SemiBold,
				                                                ),
				color = MaterialTheme.colorScheme.primary,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				modifier = Modifier.weight(1f),
			    )
			Icon(
				imageVector = Icons.AutoMirrored.Filled.OpenInNew,
				contentDescription = stringResource(
					R.string.product_details_link_content_description,
				                                   ),
				tint = MaterialTheme.colorScheme.primary,
				modifier = Modifier.size(18.dp),
			    )
		}
	}
}

@PreviewLightDark
@Composable
fun ProductLinkPreview() {
	FoodOriginTheme {
		ProductLink(
			modifier = Modifier
				.background(MaterialTheme.colorScheme.background)
				.padding(16.dp),
			value = "Value",
		                 )
	}
}
