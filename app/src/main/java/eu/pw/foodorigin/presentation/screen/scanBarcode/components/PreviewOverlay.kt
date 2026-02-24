package eu.pw.foodorigin.presentation.screen.scanBarcode.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.RegionOfInterestConfig.getRegionOfInterestRect

private val cornerRadius = 12.dp
private val frameColor = Color.White
private val frameStroke = 1.dp
private val backgroundColor = Color.Black.copy(alpha = 0.5f)

@Composable
fun PreviewOverlay(
	modifier: Modifier = Modifier,
	onExit: () -> Unit,
                  ) {
	Box(modifier = modifier.fillMaxSize()) {
		Canvas(modifier = Modifier.fillMaxSize()) {
			val rect = getRegionOfInterestRect(size.width, size.height).toComposeRect()

			clipPath(
				Path().apply {
					addRoundRect(RoundRect(rect, CornerRadius(cornerRadius.toPx())))
				},
				clipOp = ClipOp.Difference,
			        ) {
				drawRect(backgroundColor)
			}

			drawRoundRect(
				color = frameColor,
				topLeft = Offset(rect.left, rect.top),
				size = Size(rect.width, rect.height),
				cornerRadius = CornerRadius(cornerRadius.toPx()),
				style = Stroke(width = frameStroke.toPx()),
			             )
		}
		IconButton(
			onClick = onExit,
			modifier = Modifier
				.align(Alignment.TopStart)
				.padding(16.dp)
				.background(color = Color.Black.copy(alpha = 0.5f), shape = CircleShape),
		          ) {
			Icon(imageVector = Icons.Rounded.Clear, contentDescription = null, tint = Color.White)
		}
	}
}

@PreviewLightDark
@Composable
fun PreviewOverlayPreview() {
	PreviewOverlay(
		modifier = Modifier,
		onExit = {},
	              )
}