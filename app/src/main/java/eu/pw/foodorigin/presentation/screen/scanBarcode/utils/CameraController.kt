package eu.pw.foodorigin.presentation.screen.scanBarcode.utils

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController

fun getCameraController(context: Context): LifecycleCameraController {
	return LifecycleCameraController(context).apply {
		cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
		isPinchToZoomEnabled = true
		isTapToFocusEnabled = true
	}
}

