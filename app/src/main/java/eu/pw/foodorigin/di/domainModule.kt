package eu.pw.foodorigin.di

import eu.pw.foodorigin.domain.useCase.GetRelatedBigBrand
import org.koin.dsl.module

val  domainModule  = module {
	single { GetRelatedBigBrand() }
}