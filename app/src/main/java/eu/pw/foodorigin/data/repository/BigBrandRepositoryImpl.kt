package eu.pw.foodorigin.data.repository

import eu.pw.foodorigin.data.dataSource.BigBrandDataSource
import eu.pw.foodorigin.domain.BigBrand
import eu.pw.foodorigin.domain.repository.BigBrandRepository
import kotlinx.coroutines.flow.Flow

class BigBrandRepositoryImpl(private val bigBrandDataSource: BigBrandDataSource): BigBrandRepository {
	override fun getBigBrands(): Flow<Result<List<BigBrand>>> {
		return bigBrandDataSource.getBigBrands()
	}
}
