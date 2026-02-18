package eu.pw.foodorigin.presentation.screen.productdetails.model

import eu.pw.foodorigin.domain.BigBrand

data class BigBrandUi(
	val nameUi: String,
	val brandsListLink: String,
					 )

fun BigBrand.toUi() = BigBrandUi(
	nameUi = nameUI,
	brandsListLink = brandsListLink,
                                )