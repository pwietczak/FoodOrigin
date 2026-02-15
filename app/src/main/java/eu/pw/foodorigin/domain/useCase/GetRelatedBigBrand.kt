package eu.pw.foodorigin.domain.useCase

import eu.pw.foodorigin.domain.BigBrand
import eu.pw.foodorigin.domain.Product

class GetRelatedBigBrand {
	operator fun invoke(
		product: Product,
		bigBrands: List<BigBrand>,
	                   ): BigBrand? {
		return bigBrands.firstOrNull { bigBrand ->
			product.isRelatedTo(bigBrand)
		}
	}

	private fun Product.isRelatedTo(bigBrand: BigBrand): Boolean {
		if (bigBrand.keywords.isEmpty()) return false

		return matchesAnyBrand(bigBrand.keywords) ||
				matchesAnyBrandTag(bigBrand.keywords)
	}

	private fun Product.matchesAnyBrand(keywords: List<String>): Boolean {
		return keywords.any { keyword ->
			brands.isMatch(keyword)
		}
	}

	private fun Product.matchesAnyBrandTag(keywords: List<String>): Boolean {
		return brandsTags?.any { tag ->
			keywords.any { keyword -> tag.isMatch(keyword) }
		} == true
	}

	private fun String?.isMatch(keyword: String): Boolean {
		if (this == null) return false
		return this.contains(keyword, ignoreCase = true)
	}
}
