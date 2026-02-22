package eu.pw.foodorigin.presentation.screen.scanBarcode.model

import android.graphics.Rect

data class DetectedBarcode(
	val code: String,
	val boundingBox: Rect
                          )
