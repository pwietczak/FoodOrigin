package eu.pw.foodorigin.presentation.screen.scanBarcode.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.R
import eu.pw.foodorigin.presentation.screen.scanBarcode.CameraPermissionScreenState
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@Composable
internal fun CameraPermissionContent(
	modifier: Modifier = Modifier,
	state: CameraPermissionScreenState,
	onRequestPermission: () -> Unit,
	onOpenSettings: () -> Unit,
	onExit: () -> Unit,
                                    ) {
	val config = when (state) {
		CameraPermissionScreenState.Request -> CameraPermissionContentConfig(
			messageResId = R.string.camera_permission_request_message,
			buttonResId = R.string.camera_permission_button_allow,
			onButtonClick = onRequestPermission,
		                                                                    )

		CameraPermissionScreenState.Rationale -> CameraPermissionContentConfig(
			messageResId = R.string.camera_permission_rationale_message,
			buttonResId = R.string.camera_permission_button_retry,
			onButtonClick = onRequestPermission,
		                                                                      )

		CameraPermissionScreenState.Settings -> CameraPermissionContentConfig(
			messageResId = R.string.camera_permission_settings_message,
			buttonResId = R.string.camera_permission_button_settings,
			onButtonClick = onOpenSettings,
		                                                                     )
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
			text = stringResource(config.messageResId),
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center,
		    )
		Button(
			modifier = Modifier.padding(top = 16.dp),
			onClick = config.onButtonClick,
		      ) {
			Text(stringResource(config.buttonResId))
		}
		TextButton(
			modifier = Modifier.padding(top = 8.dp),
			onClick = onExit,
		          ) {
			Text(stringResource(R.string.camera_permission_button_exit))
		}
	}
}

private data class CameraPermissionContentConfig(
	val messageResId: Int,
	val buttonResId: Int,
	val onButtonClick: () -> Unit,
                                                )

private class CameraPermissionStateProvider :
	PreviewParameterProvider<CameraPermissionScreenState> {
	override val values = sequenceOf(
		CameraPermissionScreenState.Request,
		CameraPermissionScreenState.Rationale,
		CameraPermissionScreenState.Settings,
	                                )
}

@PreviewLightDark
@Composable
private fun CameraPermissionContentPreview(
	@PreviewParameter(CameraPermissionStateProvider::class) state: CameraPermissionScreenState,
                                           ) {
	FoodOriginTheme {
		Surface {
			CameraPermissionContent(
				state = state,
				onRequestPermission = {},
				onOpenSettings = {},
				onExit = {},
			                       )
		}
	}
}