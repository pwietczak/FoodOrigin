package eu.pw.foodorigin.presentation.screen.productcodeinput

sealed class ProductCodeUiEvent {
	data class NavigateToDetails(val barcode: String) : ProductCodeUiEvent()
}