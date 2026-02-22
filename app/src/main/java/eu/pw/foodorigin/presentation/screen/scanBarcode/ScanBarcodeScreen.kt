package eu.pw.foodorigin.presentation.screen.scanBarcode

import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.RegionOfInterestConfig.getRegionOfInterestRect
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.getBarcodeScanner
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.getCameraController

@Composable
fun ScanBarcodeScreen(
	modifier: Modifier = Modifier,
	viewModel: ScanBarcodeViewModel,
	onBarcodeDeprecated: (String) -> Unit,
                     ) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	LaunchedEffect(Unit) {
		viewModel.events.collect {
			when (it) {
				is ScanBarcodeEvent.BarcodeDetected -> {
					onBarcodeDeprecated(it.barcode.code)
				}
			}
		}
	}

	ScanBarcodeScreenContent(
		modifier = modifier,
		uiState = uiState,
		onBarcodeDetected = viewModel::onCodeDetected,
	                        )
}

@Composable
fun ScanBarcodeScreenContent(
	modifier: Modifier,
	uiState: ScanBarcodeUiState,
	onBarcodeDetected: (DetectedBarcode) -> Unit,
                            ) {
	val context = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current

	val barcodeScanner = remember { getBarcodeScanner() }
	val cameraController = remember { getCameraController(context) }

	BoxWithConstraints(modifier = modifier.fillMaxSize()) {
		val roiRect = remember(maxWidth, maxHeight) {
			getRegionOfInterestRect(
				constraints.maxWidth.toFloat(),
				constraints.maxHeight.toFloat(),
			                       )
		}

		DisposableEffect(lifecycleOwner) {
			cameraController.setImageAnalysisAnalyzer(
				ContextCompat.getMainExecutor(context),
				MlKitAnalyzer(
					listOf(barcodeScanner),
					COORDINATE_SYSTEM_VIEW_REFERENCED,
					ContextCompat.getMainExecutor(context),
				             ) { result: MlKitAnalyzer.Result? ->
					val barcode =
						result?.getValue(barcodeScanner)?.firstOrNull() ?: return@MlKitAnalyzer
					val boundingBox = barcode.boundingBox ?: return@MlKitAnalyzer
					val value = barcode.rawValue ?: return@MlKitAnalyzer
					if (roiRect.contains(
							boundingBox.centerX(), boundingBox.centerY(),
					                    )
					) onBarcodeDetected(DetectedBarcode(value, boundingBox))
				},
			                                         )

			cameraController.bindToLifecycle(lifecycleOwner)
			onDispose {
				cameraController.clearImageAnalysisAnalyzer()
				cameraController.unbind()
				barcodeScanner.close()
			}
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
			},
		           )
		PreviewOverlay()
		uiState.detectedBarcode?.let {
			BarcodeOverlay(modifier = Modifier, rect = it.boundingBox)
		}
	}
}
