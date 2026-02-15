package eu.pw.foodorigin.data.repository

import eu.pw.foodorigin.R
import eu.pw.foodorigin.data.dataSource.ProductDataSource
import eu.pw.foodorigin.data.mapper.ProductResultDtoMapper
import eu.pw.foodorigin.domain.LocalizedException
import eu.pw.foodorigin.domain.Product
import eu.pw.foodorigin.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
	private val dataSource: ProductDataSource,
	private val mapper: ProductResultDtoMapper
						   ) : ProductRepository {
	override fun getProduct(code: String): Flow<Result<Product>> =
		dataSource.getProduct(code).map { result ->
			result.mapCatching {
				if (it.status == 1 && it.product != null)
					mapper(it)
				else
					throw LocalizedException(
					R.string.exception_product_repository_product_not_found
					                        )
			}
		}
}