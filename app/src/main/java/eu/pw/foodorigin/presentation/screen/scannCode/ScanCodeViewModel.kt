package eu.pw.foodorigin.presentation.screen.scannCode

import android.app.Application
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import eu.pw.foodorigin.presentation.screen.scannCode.model.DetectedBarcode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanCodeViewModel(private val application: Application) : AndroidViewModel(application) {

    companion object{
        private const val AFTER_DETECTION_DELAY = 500L
    }

    private val _state = MutableStateFlow(getScanCodeUiState())
    val state: StateFlow<ScanCodeUiState> = _state.asStateFlow()
    private val barcodeScanner = provideBarcodeScanner()

    private fun getScanCodeUiState(): ScanCodeUiState{
        return ScanCodeUiState(
            cameraController = LifecycleCameraController(application).apply {
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                isPinchToZoomEnabled = true
                isTapToFocusEnabled = true
            }
                              )
    }
    private fun provideBarcodeScanner(): BarcodeScanner {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
        return BarcodeScanning.getClient(options)
    }

    fun setupAnalyzer(roiRect: android.graphics.Rect, onCodeDetected: (String) -> Unit) {
        val context = getApplication<Application>()
        state.value.cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context),
            MlKitAnalyzer(
                listOf(barcodeScanner),
                ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(context)
            ) { result ->
                if (state.value.detectedBarcode != null) return@MlKitAnalyzer

                val barcode = result?.getValue(barcodeScanner)?.firstOrNull() ?: return@MlKitAnalyzer
                val boundingBox = barcode.boundingBox ?: return@MlKitAnalyzer

                if (roiRect.contains(boundingBox.centerX(), boundingBox.centerY())) {
                    val value = barcode.rawValue ?: return@MlKitAnalyzer

                    _state.update { it.copy(detectedBarcode = DetectedBarcode(value, boundingBox)) }
                    
                    viewModelScope.launch {
                        delay(AFTER_DETECTION_DELAY)
                        onCodeDetected(value)
                    }
                    
                    state.value.cameraController.clearImageAnalysisAnalyzer()
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        state.value.cameraController.clearImageAnalysisAnalyzer()
        state.value.cameraController.unbind()
        barcodeScanner.close()
    }
}
