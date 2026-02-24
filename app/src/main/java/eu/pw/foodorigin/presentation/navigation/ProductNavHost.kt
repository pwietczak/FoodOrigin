package eu.pw.foodorigin.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.pw.foodorigin.presentation.screen.productcodeinput.ProductCodeInputScreen
import eu.pw.foodorigin.presentation.screen.productcodeinput.ProductCodeInputViewModel
import eu.pw.foodorigin.presentation.screen.productdetails.ProductDetailsScreen
import eu.pw.foodorigin.presentation.screen.scanBarcode.ScanBarcodeScreen
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

private const val KEY_SCANNED_BARCODE = "scanned_barcode"
private const val TWEEN_DURATION = 200
private fun inTransition() = fadeIn(animationSpec = tween(durationMillis = TWEEN_DURATION, delayMillis = TWEEN_DURATION))
private fun outTransition() = fadeOut(animationSpec = tween(durationMillis = TWEEN_DURATION))

@Composable
fun ProductNavHost(
	modifier: Modifier,
	navHostController: NavHostController
) {
	NavHost(
		modifier = modifier,
		navController = navHostController,
		startDestination = ProductDestination.ProductCodeInput,
		enterTransition = {inTransition()},
		popEnterTransition = {inTransition()},
		popExitTransition = {outTransition()},
		exitTransition = {outTransition()}
	) {
		composable<ProductDestination.ProductCodeInput> { backStackEntry ->
			val viewModel = koinViewModel<ProductCodeInputViewModel>()
			val scannedBarcode by backStackEntry.savedStateHandle
				.getStateFlow<String?>(KEY_SCANNED_BARCODE, null)
				.collectAsStateWithLifecycle()

			LaunchedEffect(scannedBarcode) {
				scannedBarcode?.let {
					Timber.d("ProductDestination.ProductCodeInput received scanned barcode: $it")
					viewModel.onBackFromCodeScanning(it)
					backStackEntry.savedStateHandle.remove<String>(KEY_SCANNED_BARCODE)
				}
			}

			ProductCodeInputScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = viewModel,
				navigateToScanCode = {
					Timber.d("ProductDestination.ProductCodeInput navigateToScanCode(), navigating to CodeScanning")
					navHostController.navigate(ProductDestination.CodeScanning)
				},
				onSearch = {
					Timber.d("ProductDestination.ProductCodeInput onSearch(), navigating to ProductDetails")
					navHostController.navigate(ProductDestination.ProductDetails(it))
				}
			)
		}
		composable<ProductDestination.CodeScanning> {
			ScanBarcodeScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = koinViewModel(),
				onBarcodeScanned = {
					Timber.d("ProductDestination.CodeScanning, code detected: $it, navigating back to ProductCodeInputScreen")
					navHostController.previousBackStackEntry?.savedStateHandle?.set(KEY_SCANNED_BARCODE, it)
					navHostController.popBackStack()
				},
				onExit = {
					Timber.d("ProductDestination.CodeScanning onExit(), navigating back")
					navHostController.popBackStack()
				}
			)
		}
		composable<ProductDestination.ProductDetails> {
			ProductDetailsScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = koinViewModel(),
				onBackClick = {
					Timber.d("ProductDestination.ProductDetails onBackClick(), navigating back")
					navHostController.popBackStack()
				}
			)
		}
	}
}