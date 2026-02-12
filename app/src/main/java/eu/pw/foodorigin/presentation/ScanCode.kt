package eu.pw.foodorigin.presentation

import android.graphics.Rect
import androidx.camera.core.ImageAnalysis
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun ScanCode(
	onCodeDetected: (String) -> Unit,
	modifier: Modifier = Modifier
            ) {
	// State to hold the detected barcode value
	var barcode by remember { mutableStateOf<String?>(null) }

	val context = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current

	var barcodeDetected by remember { mutableStateOf(false) }

	// State to hold the bounding rectangle of the detected barcode
	var boundingRect by remember { mutableStateOf<Rect?>(null) }

	// Initialize the camera controller with the current context
	val cameraController = remember {
		LifecycleCameraController(context)
	}

	// AndroidView to integrate the camera preview and barcode scanning
	AndroidView(
		modifier = modifier.fillMaxSize(),
		factory = { ctx ->
			PreviewView(ctx).apply {

				// Initialize the barcode scanner client with the configured options
				val barcodeScanner = getBarcodeScanner()

				// Set up the image analysis analyzer for barcode detection
				cameraController.setImageAnalysisAnalyzer(
					ContextCompat.getMainExecutor(ctx), // Use the main executor
					MlKitAnalyzer(
						listOf(barcodeScanner), // Pass the barcode scanner
						ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED, // Use view-referenced coordinates
						ContextCompat.getMainExecutor(ctx) // Use the main executor
					             ) { result: MlKitAnalyzer.Result? ->						// Process the barcode scanning results
						val barcodeResults = result?.getValue(barcodeScanner)
						if (!barcodeResults.isNullOrEmpty()) {							// Update the barcode state with the first detected barcode
							barcode = barcodeResults.first().rawValue

							// Update the state to indicate a barcode has been detected
							barcodeDetected = true

							// Update the bounding rectangle of the detected barcode
							boundingRect = barcodeResults.first().boundingBox

							Timber.d("Looking for Barcode ${barcodeResults.first().boundingBox.toString()}")
						}
					}
				                                         )

				// Bind the camera controller to the lifecycle owner
				cameraController.bindToLifecycle(lifecycleOwner)

				// Set the camera controller for the PreviewView
				this.controller = cameraController
			}
		}
	           )

	// If a QR/barcode has been detected, trigger the callback
	if (barcodeDetected) {
		LaunchedEffect(Unit) {
			delay(500)
			onCodeDetected(barcode ?: "")
		}
		DrawRectangle(rect = boundingRect)
	}
}

private fun getBarcodeScanner(): BarcodeScanner =
	BarcodeScanning.getClient(getBarcodeScannerOptions())


private fun getBarcodeScannerOptions():BarcodeScannerOptions =
	BarcodeScannerOptions.Builder()
		.setBarcodeFormats(
			Barcode.FORMAT_QR_CODE,
			Barcode.FORMAT_CODABAR,
			Barcode.FORMAT_CODE_93,
			Barcode.FORMAT_CODE_39,
			Barcode.FORMAT_CODE_128,
			Barcode.FORMAT_EAN_8,
			Barcode.FORMAT_EAN_13,
			Barcode.FORMAT_AZTEC
		                  )
		.build()

@Composable
fun DrawRectangle(rect: Rect?) {
	// Convert the Android Rect to a Compose Rect
	val composeRect = rect?.toComposeRect()

	// Draw the rectangle on a Canvas if the rect is not null
	composeRect?.let {
		Canvas(modifier = Modifier.fillMaxSize()) {
			drawRect(
				color = Color.Red,
				topLeft = Offset(it.left, it.top), // Set the top-left position
				size = Size(it.width, it.height), // Set the size of the rectangle
				style = Stroke(width = 5f) // Use a stroke style with a width of 5f
			        )
		}
	}
}