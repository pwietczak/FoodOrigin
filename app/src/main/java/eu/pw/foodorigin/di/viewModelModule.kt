package eu.pw.foodorigin.di

import eu.pw.foodorigin.presentation.screen.ScanProductViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
	viewModel { ScanProductViewModel() }
}
