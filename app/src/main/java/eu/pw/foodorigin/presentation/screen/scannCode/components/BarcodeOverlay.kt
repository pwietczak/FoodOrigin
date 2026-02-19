package eu.pw.foodorigin.presentation.screen.scannCode.components

import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.unit.dp

private val DETECTION_RECTANGLE_STROKE_WIDTH = 1.dp
private val BARCODE_OVERLAY_STROKE_COLOR = Color.Red

@Composable
fun BarcodeOverlay(rect: Rect) {
	val composeRect = rect.toComposeRect()
	Canvas(modifier = Modifier.fillMaxSize()) {
		drawRect(
			color = BARCODE_OVERLAY_STROKE_COLOR,
			topLeft = Offset(composeRect.left, composeRect.top),
			size = Size(composeRect.width, composeRect.height),
			style = Stroke(width = DETECTION_RECTANGLE_STROKE_WIDTH.toPx())
		        )
	}
}