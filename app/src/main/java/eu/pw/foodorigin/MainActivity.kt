package eu.pw.foodorigin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import eu.pw.foodorigin.presentation.navigation.ProductNavHost
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			FoodOriginTheme {
				Scaffold { innerPadding ->
					val navHostController = rememberNavController()
					ProductNavHost(
						modifier = Modifier.padding(innerPadding),
						navHostController = navHostController,
					          )
				}
			}
		}
	}
}
