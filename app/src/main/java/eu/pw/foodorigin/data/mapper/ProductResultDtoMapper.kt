package eu.pw.foodorigin.data.mapper

import eu.pw.foodorigin.data.remote.dto.ProductResultDto
import eu.pw.foodorigin.domain.Product

class ProductResultDtoMapper {
	operator fun invoke (productResultDto: ProductResultDto) = Product(
		id = productResultDto.product?.id,
		code = productResultDto.code,
		productName = productResultDto.product?.productName,
		brands = productResultDto.product?.brands,
		brandsTags = productResultDto.product?.brandsTags
	                                                           )
}