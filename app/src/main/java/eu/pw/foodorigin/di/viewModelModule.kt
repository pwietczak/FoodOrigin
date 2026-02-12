package eu.pw.foodorigin.di

import eu.pw.foodorigin.presentation.screen.productcodeinput.ProductCodeInputViewModel
import eu.pw.foodorigin.presentation.screen.productdetails.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
	viewModel { ProductCodeInputViewModel() }
	viewModel { ProductDetailsViewModel(get()) }
}
