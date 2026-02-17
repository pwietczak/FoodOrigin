package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.R
import eu.pw.foodorigin.data.remote.util.formatUrl
import timber.log.Timber

@Composable
fun ProductLink(
	modifier: Modifier,
	value: String?,
) {
	val urlToOpen = formatUrl(value)
	val uriHandler = LocalUriHandler.current

	if (urlToOpen != null && value != null) {
		Column(
			modifier = modifier
		) {
			Text(
				text = stringResource(R.string.product_details_link_label),
				style = MaterialTheme.typography.labelLarge,
				color = MaterialTheme.colorScheme.outline,
			)
			Spacer(modifier = Modifier.height(2.dp))
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.clip(RoundedCornerShape(12.dp))
					.background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
					.clickable {
						try {
							uriHandler.openUri(urlToOpen)
						} catch (e: Exception) {
							Timber.e(e, "Could not open URI: $urlToOpen")
						}
					}
					.padding(horizontal = 16.dp, vertical = 12.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween,
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
					contentDescription = stringResource(R.string.product_details_link_content_description),
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier.size(18.dp),
				)
			}
		}
	}
}

@Composable
fun LabeledElement(
	modifier: Modifier,
	isVisible: Boolean,
	label: String,
	content: @Composable ()-> Unit
				  ){
	if (isVisible) {
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
				content()
			}
		}
	}
}
