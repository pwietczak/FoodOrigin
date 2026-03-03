package eu.pw.foodorigin.presentation.screen.scanBarcode.utils

import android.graphics.Rect

/**
 * Configuration for the camera's Region of Interest (ROI) overlay.
 * Values are represented as fractions of the total container size (0.0 to 1.0).
 */
object RegionOfInterestConfig {
	const val WIDTH_FRACTION = 0.8f
	const val HEIGHT_FRACTION = 0.3f

	fun getRegionOfInterestRect(screenWidth: Float, screenHeight: Float): Rect {
		val shortSide = minOf(screenWidth, screenHeight)
		val longSide = maxOf(screenWidth, screenHeight)

		val roiWidth = shortSide * WIDTH_FRACTION
		val roiHeight = longSide * HEIGHT_FRACTION
		val left = (screenWidth - roiWidth) / 2f
		val top = (screenHeight - roiHeight) / 2f

		return Rect(
			left.toInt(),
			top.toInt(),
			(left + roiWidth).toInt(),
			(top + roiHeight).toInt()
		           )
	}

	fun Rect.isCenterInsideROI(screenWidth: Float, screenHeight: Float): Boolean {
		val roi = getRegionOfInterestRect(screenWidth, screenHeight)
		return roi.contains(this.centerX(), this.centerY())
	}
}