package eu.pw.foodorigin.presentation.screen

import android.Manifest
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@Composable
fun ScanProductScreen(
	modifier: Modifier,
	viewModel: ScanProductViewModel) {

	ScanProductScreenContent(
		modifier = modifier
							)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ScanProductScreenContent(
	modifier: Modifier) {
//	Box(
//		modifier = modifier.fillMaxSize(),
//		contentAlignment = Alignment.Center
//	   ) {
//		Text(text = "Scan Screen")
//	}
}

@PreviewLightDark
@Composable
fun ScanProductScreenPreview(){
	FoodOriginTheme {
		ScanProductScreenContent(
			modifier = Modifier,
		)
	}
}