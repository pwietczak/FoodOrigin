package eu.pw.foodorigin.presentation.screen.scanBarcode.utils

import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning

fun getBarcodeScanner(): BarcodeScanner {
	val options = BarcodeScannerOptions.Builder()
		.enableAllPotentialBarcodes()
		.build()
	return BarcodeScanning.getClient(options)
}
