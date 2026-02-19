package eu.pw.foodorigin.presentation.screen.scannCode

import eu.pw.foodorigin.presentation.screen.scannCode.model.RectDimensions

/**
 * Configuration for the camera's Region of Interest (ROI) overlay.
 * Values are represented as fractions of the total container size (0.0 to 1.0).
 */
object RoiConfig {
	const val WIDTH_FRACTION = 0.8f
	const val HEIGHT_FRACTION = 0.2f

	fun getRoiDimensions(screenWidth: Float, screenHeight: Float): RectDimensions {
		val roiWidth = screenWidth * WIDTH_FRACTION
		val roiHeight = screenHeight * HEIGHT_FRACTION
		val left = (screenWidth - roiWidth) / 2f
		val top = (screenHeight - roiHeight) / 2f

		return RectDimensions(
			left = left,
			top = top,
			width = roiWidth,
			height = roiHeight
		                     )
	}
}