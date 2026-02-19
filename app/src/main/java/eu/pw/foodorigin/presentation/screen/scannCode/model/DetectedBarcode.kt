package eu.pw.foodorigin.presentation.screen.scannCode.model

import android.graphics.Rect

data class DetectedBarcode(
	val value: String,
	val boundingBox: Rect
                          )
