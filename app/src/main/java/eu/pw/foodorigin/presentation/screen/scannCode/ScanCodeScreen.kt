package eu.pw.foodorigin.presentation.screen.scannCode

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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.foodorigin.presentation.screen.scannCode.components.BarcodeOverlay
import eu.pw.foodorigin.presentation.screen.scannCode.components.ScanningViewfinder
import eu.pw.foodorigin.presentation.screen.scannCode.RoiConfig.getRoiDimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanCodeScreen(
	onCodeDetected: (String) -> Unit,
	modifier: Modifier = Modifier,
	viewModel: ScanCodeViewModel = koinViewModel(),
                  ) {
	val state by viewModel.state.collectAsStateWithLifecycle()
	val lifecycleOwner = LocalLifecycleOwner.current

	DisposableEffect(lifecycleOwner) {
		state.cameraController.bindToLifecycle(lifecycleOwner)
		onDispose {
			state.cameraController.unbind()
		}
	}

	BoxWithConstraints(modifier = modifier.fillMaxSize()) {
		val roiRect = remember(maxWidth, maxHeight) {
			val roiDims = getRoiDimensions(
				constraints.maxWidth.toFloat(),
				constraints.maxHeight.toFloat()
			                              )

			android.graphics.Rect(
				roiDims.left.toInt(),
				roiDims.top.toInt(),
				(roiDims.left + roiDims.width).toInt(),
				(roiDims.top + roiDims.height).toInt()
			                     )
		}

		LaunchedEffect(roiRect) {
			viewModel.setupAnalyzer(roiRect, onCodeDetected)
		}

		Box(modifier = Modifier.fillMaxSize()) {
			AndroidView(
				modifier = Modifier.fillMaxSize(),
				factory = { ctx ->
					PreviewView(ctx).apply {
						scaleType = PreviewView.ScaleType.FILL_CENTER
						controller = state.cameraController
					}
				}
			           )

			ScanningViewfinder()

			state.detectedBarcode?.let { barcode ->
				BarcodeOverlay(barcode.boundingBox)
			}
		}
	}
}