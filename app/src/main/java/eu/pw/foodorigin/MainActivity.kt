package eu.pw.foodorigin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import eu.pw.foodorigin.presentation.HomeScreen
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			FoodOriginTheme {
				Scaffold { innerPadding ->
					HomeScreen(modifier = Modifier.padding(innerPadding))
//					ScanProductScreen(
//						modifier = Modifier.padding(innerPadding),
//						viewModel = koinViewModel<ScanProductViewModel>()
//							  )
				}
			//FoodOriginApp()
			}
		}
	}
} //
//@PreviewScreenSizes
//@Composable
//fun FoodOriginApp() {
//	var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
//
//	NavigationSuiteScaffold(
//		navigationSuiteItems = {
//			AppDestinations.entries.forEach {
//				item(
//					icon = {
//						Icon(
//							it.icon,
//							contentDescription = it.label
//						    )
//					},
//					label = { Text(it.label) },
//					selected = it == currentDestination,
//					onClick = { currentDestination = it }
//				    )
//			}
//		}
//	                       ) {
//		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//			Greeting(
//				name = "Android",
//				modifier = Modifier.padding(innerPadding)
//			        )
//		}
//	}
//}
//
//enum class AppDestinations(
//	val label: String,
//	val icon: ImageVector,
//                          ) {
//	HOME("Home", Icons.Default.Home),
//	FAVORITES("Favorites", Icons.Default.Favorite),
//	PROFILE("Profile", Icons.Default.AccountBox),
//}
//
//@Composable
//fun Greeting(
//	name: String,
//	modifier: Modifier = Modifier
//            ) {
//	Text(
//		text = "Hello $name!",
//		modifier = modifier
//	    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//	FoodOriginTheme {
//		Greeting("Android")
//	}
//}