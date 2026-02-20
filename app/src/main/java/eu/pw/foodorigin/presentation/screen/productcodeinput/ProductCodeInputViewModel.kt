package eu.pw.foodorigin.presentation.screen.productcodeinput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.pw.foodorigin.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductCodeInputViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(ProductCodeInputUiState())
	val uiState: StateFlow<ProductCodeInputUiState> = _uiState.asStateFlow()

	private val _eventChannel = Channel<ProductCodeUiEvent>()
	val events = _eventChannel.receiveAsFlow()

	fun onBarcodeChange(newBarcode: String) {
		_uiState.update { it.copy(barcode = newBarcode, errorMessageResId = null) }
	}

	fun onSearchClicked() {
		val currentBarcode = _uiState.value.barcode.trim()

		if (!validate(currentBarcode)) return

		viewModelScope.launch {
			_uiState.update { it.copy(isLoading = true) }
			_eventChannel.send(ProductCodeUiEvent.NavigateToDetails(currentBarcode))
			_uiState.update { it.copy(isLoading = false) }
		}
	}

	private fun validate(barcode: String): Boolean{
		if (barcode.isBlank()) {
			_uiState.update { it.copy(errorMessageResId = R.string.product_code_input_error_empty_barcode) }
			return false
		}
		return true
	}
}