package eu.pw.foodorigin.presentation.screen.scanBarcode

import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.foodorigin.presentation.screen.scanBarcode.components.BarcodeOverlay
import eu.pw.foodorigin.presentation.screen.scanBarcode.components.PreviewOverlay
import eu.pw.foodorigin.presentation.screen.scanBarcode.model.DetectedBarcode
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.RegionOfInterestConfig.isCenterInsideROI
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.getBarcodeScanner
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.getCameraController

@Composable
fun ScanBarcodeScreen(
	modifier: Modifier = Modifier,
	viewModel: ScanBarcodeViewModel,
	onBarcodeScanned: (String) -> Unit,
	onExit: () -> Unit,
                     ) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	LaunchedEffect(Unit) {
		viewModel.events.collect {
			when (it) {
				is ScanBarcodeEvent.BarcodeScanned -> {
					onBarcodeScanned(it.code)
				}
			}
		}
	}

	ScanBarcodeScreenContent(
		modifier = modifier,
		uiState = uiState,
		onBarcodeDetected = viewModel::onCodeDetected,
		onExit = onExit,
	                        )
}

@Composable
fun ScanBarcodeScreenContent(
	modifier: Modifier,
	uiState: ScanBarcodeUiState,
	onBarcodeDetected: (DetectedBarcode) -> Unit,
	onExit: () -> Unit,
	) {
	val context = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current

	val barcodeScanner = remember { getBarcodeScanner() }
	val cameraController = remember {
		getCameraController(context).apply {
			bindToLifecycle(lifecycleOwner)
		}
	}

	Box(modifier = modifier.fillMaxSize()) {
		AndroidView(
			modifier = Modifier.fillMaxSize(),
			factory = { ctx ->
				PreviewView(ctx).apply {
					scaleType = PreviewView.ScaleType.FILL_CENTER
					controller = cameraController
					cameraController.setImageAnalysisAnalyzer(
						ContextCompat.getMainExecutor(context),
						MlKitAnalyzer(
							listOf(barcodeScanner),
							COORDINATE_SYSTEM_VIEW_REFERENCED,
							ContextCompat.getMainExecutor(context),
						             ) { result: MlKitAnalyzer.Result? ->
							val barcode = result?.getValue(barcodeScanner)?.firstOrNull()
							val boundingBox = barcode?.boundingBox
							val value = barcode?.rawValue
							if (value != null && boundingBox != null) {
								if (boundingBox.isCenterInsideROI(width.toFloat(), height.toFloat()))
									onBarcodeDetected(DetectedBarcode(value, boundingBox))
							}
						},
					                                         )
				}
			},
		           )
		PreviewOverlay(onExit = onExit)
		uiState.detectedBarcode?.let {
			BarcodeOverlay(modifier = Modifier, rect = it.boundingBox)
		}
	}

	DisposableEffect(lifecycleOwner) {
		onDispose {
			cameraController.clearImageAnalysisAnalyzer()
			cameraController.unbind()
			barcodeScanner.close()
		}
	}
}
