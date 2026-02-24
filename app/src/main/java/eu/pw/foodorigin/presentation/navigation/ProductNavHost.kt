package eu.pw.foodorigin.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.pw.foodorigin.presentation.screen.productcodeinput.ProductCodeInputScreen
import eu.pw.foodorigin.presentation.screen.productcodeinput.ProductCodeInputViewModel
import eu.pw.foodorigin.presentation.screen.productdetails.ProductDetailsScreen
import eu.pw.foodorigin.presentation.screen.scanBarcode.ScanBarcodeScreen
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun ProductNavHost(
	modifier: Modifier,
	navHostController: NavHostController
                  ) {
	NavHost(
		modifier= Modifier,
		navController = navHostController,
		startDestination = ProductDestination.ProductCodeInput,
		   ){
		composable<ProductDestination.ProductCodeInput>{
			ProductCodeInputScreen(
				modifier = modifier,
				viewModel = koinViewModel<ProductCodeInputViewModel>(),
				navigateToScanCode = {
					Timber.d("ProductCodeInput navigateToScanCode(), navigating to CodeScanning")
					navHostController.navigate(ProductDestination.CodeScanning)
				},
				onSearch = {
					Timber.d("ProductCodeInput onSearch(), navigating to ProductDetails")
					navHostController.navigate(ProductDestination.ProductDetails(it))
				}
			)
		}
		composable<ProductDestination.CodeScanning>{
			ScanBarcodeScreen(
				modifier = modifier,
				viewModel = koinViewModel(),
				onBarcodeScanned = {
					Timber.d("Code detected: $it navigating to ProductDetails")
					navHostController.navigate(ProductDestination.ProductDetails(it)) {
						popUpTo(ProductDestination.ProductCodeInput)
					}
				},
				onExit = {
					Timber.d("CodeScanning onExit(), navigating back")
					navHostController.popBackStack()
				}
						  )
		}
		composable<ProductDestination.ProductDetails>{
			ProductDetailsScreen(
				modifier = modifier,
				viewModel = koinViewModel(),
				onBackClick = {
					Timber.d("ProductDetails onBackClick(), navigating back")
					navHostController.popBackStack()}
			)
		}
	}
}