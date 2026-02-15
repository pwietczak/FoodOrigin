package eu.pw.foodorigin.data.dataSource

import eu.pw.foodorigin.domain.BigBrand
import kotlinx.coroutines.flow.Flow

interface BigBrandDataSource {
	fun getBigBrands(): Flow<Result<List<BigBrand>>>
}