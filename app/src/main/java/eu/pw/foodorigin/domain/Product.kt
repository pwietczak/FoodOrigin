package eu.pw.foodorigin.domain

data class Product(
	val id: String?,
	val code: String?,
	val productName: String?,
	val brands: String?,
	val brandsTags: List<String>?
				  )
