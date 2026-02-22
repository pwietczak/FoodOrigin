package eu.pw.foodorigin.presentation.screen.scanBarcode

import eu.pw.foodorigin.presentation.screen.scanBarcode.model.DetectedBarcode

data class ScanBarcodeUiState(
	val detectedBarcode: DetectedBarcode? = null
                             )