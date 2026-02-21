package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import eu.pw.foodorigin.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProductImage(
	modifier: Modifier,
	imageUrl: String?,
                ) {
	if (!imageUrl.isNullOrBlank()) {
		val previewHandler = AsyncImagePreviewHandler {
			ColorImage(Color.Red.toArgb())
		}
		CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
			SubcomposeAsyncImage(
				modifier = modifier,
				model = ImageRequest
					.Builder(LocalContext.current)
					.data(imageUrl)
					.crossfade(true)
					.build(),
				loading = {
					CircularProgressIndicator()
				},
				contentDescription = stringResource(
					R.string.product_details_product_image_content_description,
				                                   ),
			                    )
		}
	}
}