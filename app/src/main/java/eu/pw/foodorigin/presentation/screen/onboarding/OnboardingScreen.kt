package eu.pw.foodorigin.presentation.screen.onboarding

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import eu.pw.foodorigin.R
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(
	modifier: Modifier = Modifier,
	onOnboardingComplete: () -> Unit,
                    ) {
	val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

	if (permissionState.status.isGranted) {
		onOnboardingComplete()
	}

	var onCancel by remember(permissionState.status.shouldShowRationale) {
		mutableStateOf(permissionState.status.shouldShowRationale)
	}

	if (onCancel) {
		ShowRationaleDialog(
			onDismiss = { onCancel = false },
			onConfirm = { permissionState.launchPermissionRequest() },
			textArgs = arrayOf("Manifest.permission.CAMERA")
		                   )
	}

	val textResId =
		if (permissionState.status.shouldShowRationale)
			R.string.onboarding_screen_text_show_rationale
		else
			R.string.onboarding_screen_text_please_grant_permission


	Box(
		modifier = modifier.fillMaxSize(),
	   ) {
		Column(
			modifier = Modifier
				.align(Alignment.Center)
				.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		      ) {
			Text(
				text = stringResource(textResId),
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.bodyLarge,
				color = MaterialTheme.colorScheme.onBackground,
			    )
			Spacer(modifier = Modifier.height(16.dp))
			Button(onClick = { permissionState.launchPermissionRequest() }) {
				Text(stringResource(R.string.onboarding_screen_button_grant_permission))
			}
		}
	}
}

@PreviewLightDark
@Composable
fun OnboardingScreenPreview() {
	FoodOriginTheme {
		OnboardingScreen(
			modifier = Modifier.background(MaterialTheme.colorScheme.background),
			onOnboardingComplete = {},
		                )
	}
}
