package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import eu.pw.foodorigin.data.remote.util.formatUrl
import eu.pw.foodorigin.presentation.screen.productdetails.DetailRow
import timber.log.Timber

@Composable
fun ProductLink(
	modifier: Modifier,
	value: String?,
) {
	val urlToOpen = formatUrl(value)
	if (urlToOpen != null) {
		val uriHandler = LocalUriHandler.current

		DetailRow(
			modifier = modifier.clickable {
				try {
					uriHandler.openUri(urlToOpen)
				} catch (e: Exception) {
					Timber.e(e, "Could not open URI: $urlToOpen")
				}
			},
			label = "link",
			value = value,
		)
	}
}
