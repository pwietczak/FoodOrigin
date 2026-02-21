package eu.pw.foodorigin.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
	val id: String? = null,
	@SerialName("product_name")
	val productName: String? = null,
	val brands: String? = null,
	@SerialName("brands_tags")
	val brandsTags: List<String>? = null,
	@SerialName("image_url")
	val imageUrl: String? = null,
	val quantity: String? = null,
	val link: String? = null,

)