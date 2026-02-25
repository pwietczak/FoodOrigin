package eu.pw.foodorigin.di

import eu.pw.foodorigin.presentation.screen.productcodeinput.ProductCodeInputViewModel
import eu.pw.foodorigin.presentation.screen.productdetails.ProductDetailsViewModel
import eu.pw.foodorigin.presentation.screen.scanBarcode.ScanBarcodeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProductCodeInputViewModel() }
    viewModel {
        ProductDetailsViewModel(
            savedStateHandle = get(),
            productRepository = get(),
            bigBrandRepository = get(),
            getRelatedBigBrand = get()
        )
    }
    viewModel { ScanBarcodeViewModel(
        vibrationService = get()
                                    ) }
}
