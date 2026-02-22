package eu.pw.foodorigin.presentation.screen.scanBarcode

import eu.pw.foodorigin.presentation.screen.scanBarcode.model.DetectedBarcode

sealed interface ScanBarcodeEvent {
	data class BarcodeDetected(val barcode: DetectedBarcode): ScanBarcodeEvent
}
