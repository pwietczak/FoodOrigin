package eu.pw.foodorigin.presentation.screen.scanBarcode

sealed interface ScanBarcodeEvent {
	data class BarcodeScanned(val code: String): ScanBarcodeEvent
}
