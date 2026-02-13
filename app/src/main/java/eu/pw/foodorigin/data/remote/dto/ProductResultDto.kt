package eu.pw.foodorigin.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResultDto(
	val code: String? = null,
	val status: Int? = null,
	val product: ProductDto? = null
)