package eu.pw.foodorigin.presentation.screen.productdetails.model

import eu.pw.foodorigin.domain.Product

data class ProductUi(
			val id: String?,
			val productName: String?,
			val brand: String?,
			val code: String? = null,
			val imageUrl: String?,
			val quantity: String? = null,
			val link: String? = null,
					)

fun Product.toProductUi()=
		ProductUi(
			id = id,
			productName = productName,
			brand = brands,
			code = code,
			imageUrl = imageUrl,
			quantity = quantity,
			link = link,
					   )
