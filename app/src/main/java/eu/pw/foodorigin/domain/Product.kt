package eu.pw.foodorigin.domain

import eu.pw.foodorigin.data.remote.dto.ProductResultDto

data class Product(
	val id: String?,
	val code: String?,
	val productName: String?,
	val brands: String?,
	val brandsTags: List<String>?
				  )

fun ProductResultDto.toProduct()=
	Product(
		id = product?.id,
		code = code,
		productName = product?.productName,
		brands = product?.brands,
		brandsTags = product?.brandsTags
	       )