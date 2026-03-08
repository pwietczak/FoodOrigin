package eu.pw.foodorigin.presentation.screen.scanBarcode.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.presentation.screen.scanBarcode.model.DetectedBarcode

private val DETECTION_RECTANGLE_STROKE_WIDTH = 1.dp
private val BARCODE_OVERLAY_STROKE_COLOR = Color.Red

@Composable
fun BarcodeOverlay(
	modifier: Modifier,
	barcode: DetectedBarcode,
                  ) {
	val width = barcode.boundingBox.right - barcode.boundingBox.left
	val height = barcode.boundingBox.bottom - barcode.boundingBox.top
	Canvas(modifier = modifier.fillMaxSize()) {
		drawRect(
			color = BARCODE_OVERLAY_STROKE_COLOR,
			topLeft = Offset(x = barcode.boundingBox.left, y = barcode.boundingBox.top),
			size = Size(width = width, height = height),
			style = Stroke(width = DETECTION_RECTANGLE_STROKE_WIDTH.toPx()),
		        )
	}
}