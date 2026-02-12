package eu.pw.foodorigin.presentation.screen.onboarding

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.pw.foodorigin.R
import eu.pw.foodorigin.ui.theme.FoodOriginTheme

@Composable
fun ShowRationaleDialog(
	titleResId: Int = R.string.show_rationale_dialog_title,
	textResId: Int = R.string.show_rationale_dialog_text,
	textArgs: Array<String> = emptyArray(),
	onConfirm: () -> Unit,
	onDismiss: () -> Unit,
                       ) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = {
			Text(text = stringResource(titleResId))
		},
		text = {
			Text(text = stringResource(textResId,*textArgs))
		},
		confirmButton = {
			TextButton(onClick = onConfirm) {
				Text(stringResource(R.string.show_rationale_dialog_button_confirm))
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text(stringResource(R.string.show_rationale_dialog_button_cancel, ))
			}
		},
	           )
}

@PreviewLightDark
@Composable
fun ShowRationaleDialogPreview() {
	FoodOriginTheme {
		ShowRationaleDialog(
			onDismiss = {},
			onConfirm = {},
			textArgs = arrayOf("Manifest.permission.CAMERA"),
		                   )
	}
}