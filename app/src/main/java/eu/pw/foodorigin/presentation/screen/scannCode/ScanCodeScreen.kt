package eu.pw.foodorigin.presentation.screen.scannCode

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import eu.pw.foodorigin.presentation.screen.scannCode.components.BarcodeOverlay
import eu.pw.foodorigin.presentation.screen.scannCode.model.DetectedBarcode
import timber.log.Timber

@Composable
fun ScanCodeScreen(
	onCodeDetected: (String) -> Unit,
	modifier: Modifier = Modifier,
                  ) {
	val context = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current

	val barcodeScanner = remember { BarcodeScannerProvider.provide() }
	val cameraController = remember {
		LifecycleCameraController(context).apply {
			cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
			isPinchToZoomEnabled = true
			isTapToFocusEnabled = true
		}
	}

	var detectedBarcode by remember { mutableStateOf<DetectedBarcode?>(null) }
	var isHandlingResult by remember { mutableStateOf(false) }

	LaunchedEffect(Unit) {
		cameraController.setImageAnalysisAnalyzer(
			ContextCompat.getMainExecutor(context),
			MlKitAnalyzer(
				listOf(barcodeScanner),
				ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED,
				ContextCompat.getMainExecutor(context)
			             ) { result ->

				if (isHandlingResult) return@MlKitAnalyzer

				val barcode = result
					?.getValue(barcodeScanner)
					?.firstOrNull()

				val value = barcode?.rawValue ?: return@MlKitAnalyzer

				isHandlingResult = true

				cameraController.clearImageAnalysisAnalyzer()
				detectedBarcode = DetectedBarcode(
					value = value,
					boundingBox = barcode.boundingBox
				                                 )
			}
		                                         )
	}

	LaunchedEffect(detectedBarcode) {
		detectedBarcode?.let {
			onCodeDetected(it.value)
		}
	}

	DisposableEffect(Unit) {
		cameraController.bindToLifecycle(lifecycleOwner)
		onDispose {
			barcodeScanner.close()
			cameraController.unbind()
		}
	}

	Box(modifier = modifier.fillMaxSize()) {
		AndroidView(
			modifier = Modifier.fillMaxSize(),
			factory = { ctx ->
				PreviewView(ctx).apply {
					scaleType = PreviewView.ScaleType.FILL_CENTER
					controller = cameraController
				}
			}
		           )
		//TODO add pinch to zoom and tap to focus info for user and maybe more overlay

		detectedBarcode?.boundingBox?.let { rect ->
			Timber.d("Detected barcode bounding box: $rect")
			BarcodeOverlay(rect)
		}
	}
}
