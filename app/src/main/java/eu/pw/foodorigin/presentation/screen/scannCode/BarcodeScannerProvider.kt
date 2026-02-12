package eu.pw.foodorigin.presentation.screen.scannCode

import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

object BarcodeScannerProvider {
	fun provide(): BarcodeScanner {
		val options = BarcodeScannerOptions.Builder()
			.setBarcodeFormats(
				Barcode.FORMAT_CODE_128,
				Barcode.FORMAT_EAN_8,
				Barcode.FORMAT_EAN_13
			                  )
			.build()

		return BarcodeScanning.getClient(options)
	}
}