package eu.pw.foodorigin.domain.repository

import eu.pw.foodorigin.domain.BigBrand
import kotlinx.coroutines.flow.Flow

interface BigBrandRepository {
	fun getBigBrands(): Flow<Result<List<BigBrand>>>
}