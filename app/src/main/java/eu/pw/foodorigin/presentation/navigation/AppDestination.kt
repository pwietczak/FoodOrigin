package eu.pw.foodorigin.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {
	@Serializable
	object Onboarding : AppDestination

	@Serializable
	object FoodOrigin : AppDestination
}
