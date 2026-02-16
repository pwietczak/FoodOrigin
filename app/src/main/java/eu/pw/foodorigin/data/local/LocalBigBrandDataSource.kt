package eu.pw.foodorigin.data.local

import eu.pw.foodorigin.data.dataSource.BigBrandDataSource
import eu.pw.foodorigin.domain.BigBrand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalBigBrandDataSource : BigBrandDataSource {
	override fun getBigBrands(): Flow<Result<List<BigBrand>>> {
		return flow {
			emit(Result.success(bigBrands))
		}
	}

	private val nestleBrand = BigBrand(
		nameUI = "Nestlé",
		keywords = listOf(
			"Nestlé",
			"Nestle",
			"Winiary",
			"Maggi",
			"KitKat",
			"Nescafé",
			"Nescafe",
			"Purina",
		                 ),
	                                  )

	private val bigBrands = listOf(
		nestleBrand,
	                              )
}