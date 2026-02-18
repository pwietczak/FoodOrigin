package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.R
import eu.pw.foodorigin.data.remote.util.normalizeUrl
import eu.pw.foodorigin.presentation.screen.productdetails.model.BigBrandUi
import timber.log.Timber

@Composable
fun BigBrandInformation(
	modifier: Modifier = Modifier,
	relatedBigBrand: BigBrandUi?,
                       ) {
	relatedBigBrand?.let { bigBrand ->
		val urlToOpen = normalizeUrl(bigBrand.brandsListLink)
		val uriHandler = LocalUriHandler.current

		Row(
			modifier = modifier
				.clip(RoundedCornerShape(12.dp))
				.background(MaterialTheme.colorScheme.errorContainer)
				.clickable(enabled = urlToOpen != null) {
					try {
						uriHandler.openUri(urlToOpen!!)
					}
					catch (e: Exception) {
						Timber.e(e, "Could not open URI: $urlToOpen")
					}
				}
				.padding(horizontal = 16.dp, vertical = 12.dp),
			verticalAlignment = Alignment.CenterVertically,
		   ) {
			Icon(
				imageVector = Icons.Default.Warning,
				contentDescription = null,
				tint = MaterialTheme.colorScheme.error,
				modifier = Modifier
					.size(32.dp),
			    )
			Text(
				text = stringResource(R.string.product_details_big_brand_information, bigBrand.nameUi),
				style = MaterialTheme.typography.bodyLarge,
				color = MaterialTheme.colorScheme.onErrorContainer,
				modifier = Modifier
					.weight(1f)
					.padding(horizontal = 8.dp)
			    )
			urlToOpen?.let {
				Icon(
					imageVector = Icons.AutoMirrored.Filled.OpenInNew,
					contentDescription = stringResource(
						R.string.product_details_link_content_description,
					                                   ),
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier
						.size(32.dp),
				    )
			}
		}
	}
}

@PreviewLightDark
@Composable
fun BigBrandInformationPreview() {
	BigBrandInformation(
		modifier = Modifier,
		relatedBigBrand = BigBrandUi(
			nameUi = "ECorp",
			brandsListLink = "www.ecorp.com",
		                            ),
	                   )
}
