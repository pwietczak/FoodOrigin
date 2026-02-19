package eu.pw.foodorigin.presentation.screen.scannCode

import androidx.camera.view.LifecycleCameraController
import eu.pw.foodorigin.presentation.screen.scannCode.model.DetectedBarcode

data class ScanCodeUiState(
	val cameraController: LifecycleCameraController,
	val detectedBarcode: DetectedBarcode? = null,
                          )
