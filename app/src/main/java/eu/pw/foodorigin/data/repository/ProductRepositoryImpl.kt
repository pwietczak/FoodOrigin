package eu.pw.foodorigin.data.repository

import eu.pw.foodorigin.data.dataSource.ProductDataSource
import eu.pw.foodorigin.domain.Product
import eu.pw.foodorigin.domain.repository.ProductRepository
import eu.pw.foodorigin.domain.toProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(private val dataSource: ProductDataSource) : ProductRepository {
	override fun getProduct(code: String): Flow<Result<Product>> =
		dataSource.getProduct(code).map { result ->
			result.mapCatching {
				if (it.status == 1 && it.product != null) it.toProduct()
				else throw Exception("Product not found")
			}
		}
}