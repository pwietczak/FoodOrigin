package eu.pw.foodorigin.presentation.screen.scannCode.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.presentation.screen.scannCode.RoiConfig.getRoiDimensions

private val cornerRadius = 12.dp
private val frameColor = Color.White
private val frameStroke = 2.dp
private val backgroundColor = Color.Black.copy(alpha = 0.5f)

@Composable
fun ScanningViewfinder(
	modifier: Modifier = Modifier
                      ) {
	Canvas(modifier = modifier.fillMaxSize()) {
		val roiDims = getRoiDimensions(size.width, size.height)

		val rect = Rect(
			left = roiDims.left,
			top = roiDims.top,
			right = roiDims.left + roiDims.width,
			bottom = roiDims.top + roiDims.height
		               )

		// Background overlay (darker area outside the ROI)
		clipPath(
			Path().apply {
				addRoundRect(RoundRect(rect, CornerRadius(cornerRadius.toPx())))
			},
			clipOp = ClipOp.Difference
		        ) {
			drawRect(backgroundColor)
		}

		// ROI Frame - Region of Interest
		drawRoundRect(
			color = frameColor,
			topLeft = Offset(roiDims.left, roiDims.top),
			size = Size(roiDims.width, roiDims.height),
			cornerRadius = CornerRadius(cornerRadius.toPx()),
			style = Stroke(width = frameStroke.toPx())
		             )
	}
}


@PreviewLightDark
@Composable
fun ScanningViewfinderPreview() {
	ScanningViewfinder()
}