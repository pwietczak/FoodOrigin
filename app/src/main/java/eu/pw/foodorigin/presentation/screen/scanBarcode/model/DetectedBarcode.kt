package eu.pw.foodorigin.presentation.screen.scanBarcode.model

data class BarcodeBoundingBox(
	val left: Float,
	val top: Float,
	val right: Float,
	val bottom: Float
)

data class DetectedBarcode(
	val code: String,
	val boundingBox: BarcodeBoundingBox
)
