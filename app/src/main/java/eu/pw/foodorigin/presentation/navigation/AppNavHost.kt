package eu.pw.foodorigin.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.pw.foodorigin.presentation.screen.onboarding.OnboardingScreen

@Composable
fun AppNavHost(
	modifier: Modifier,
	navHostController: NavHostController
              ) {
	NavHost(
		modifier= Modifier,
		navController = navHostController,
		startDestination = AppDestination.Onboarding,){
		composable<AppDestination.Onboarding>{
			OnboardingScreen(
				modifier = modifier,
				onOnboardingComplete = {
					navHostController.navigate(AppDestination.FoodOrigin){
						popUpTo(AppDestination.Onboarding){
							inclusive = true
						}
					}
				}
			)
		}
		composable<AppDestination.FoodOrigin>{
			val navHostControllerFD = rememberNavController()
			ProductNavHost(modifier = modifier, navHostController = navHostControllerFD)
		}
	}

}