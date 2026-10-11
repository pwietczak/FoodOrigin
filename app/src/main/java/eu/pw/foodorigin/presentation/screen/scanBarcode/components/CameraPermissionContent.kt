package eu.pw.foodorigin.presentation.screen.scanBarcode.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.R
import eu.pw.foodorigin.presentation.screen.scanBarcode.CameraPermissionScreenState

@Composable
internal fun CameraPermissionContent(
	modifier: Modifier = Modifier,
	state: CameraPermissionScreenState,
	onRequestPermission: () -> Unit,
	onOpenSettings: () -> Unit,
	onExit: () -> Unit,
) {
	val message = when (state) {
		CameraPermissionScreenState.Request -> R.string.camera_permission_request_message
		CameraPermissionScreenState.Rationale -> R.string.camera_permission_rationale_message
		CameraPermissionScreenState.Settings -> R.string.camera_permission_settings_message
	}

	Column(
		modifier = modifier
			.fillMaxSize()
			.padding(24.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Text(
			text = stringResource(R.string.camera_permission_title),
			style = MaterialTheme.typography.headlineSmall,
			textAlign = TextAlign.Center,
		)
		Text(
			modifier = Modifier.padding(top = 16.dp),
			text = stringResource(message),
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center,
		)
		if (state == CameraPermissionScreenState.Settings) {
			Button(
				modifier = Modifier.padding(top = 16.dp),
				onClick = onOpenSettings,
			) {
				Text(stringResource(R.string.camera_permission_button_settings))
			}
		} else {
			Button(
				modifier = Modifier.padding(top = 16.dp),
				onClick = onRequestPermission,
			) {
				Text(
					stringResource(
						if (state == CameraPermissionScreenState.Request)
							R.string.camera_permission_button_allow
						else
							R.string.camera_permission_button_retry,
					),
				)
			}
		}
		TextButton(
			modifier = Modifier.padding(top = 8.dp),
			onClick = onExit,
		) {
			Text(stringResource(R.string.camera_permission_button_exit))
		}
	}
}
