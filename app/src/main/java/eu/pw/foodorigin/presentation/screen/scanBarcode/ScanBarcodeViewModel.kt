package eu.pw.foodorigin.presentation.screen.scanBarcode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.pw.foodorigin.presentation.screen.scanBarcode.model.DetectedBarcode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class ScanBarcodeViewModel: ViewModel() {

	companion object{
		private const val AFTER_DETECTION_DELAY = 200L
	}

	private val _uiState = MutableStateFlow(ScanBarcodeUiState())
	val uiState = _uiState.asStateFlow()

	private val _events = Channel<ScanBarcodeEvent>()
	val events = _events.receiveAsFlow()

	fun onCodeDetected(detectedBarcode: DetectedBarcode) {
		if (isBarcodeValid(detectedBarcode)) {
			Timber.d("############### Detected Barcode: $detectedBarcode")
			_uiState.update { it.copy(detectedBarcode = detectedBarcode) }
			viewModelScope.launch {
				delay(AFTER_DETECTION_DELAY)
				_events.trySend(ScanBarcodeEvent.BarcodeDetected(detectedBarcode))
			}
		}
	}

	private fun isBarcodeValid(barcode: DetectedBarcode): Boolean {
		return barcode.code.isNotBlank()
	}
}

