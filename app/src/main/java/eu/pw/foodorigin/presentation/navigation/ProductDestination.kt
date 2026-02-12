package eu.pw.foodorigin.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface ProductDestination {
	@Serializable
	object ProductCodeInput : ProductDestination

	@Serializable
	object CodeScanning : ProductDestination

	@Serializable
	data class ProductDetails(
		val productCode: String,
							 ) : ProductDestination
}