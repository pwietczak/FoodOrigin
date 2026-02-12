package eu.pw.foodorigin.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.shouldShowRationale
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

	// State to hold the scanned barcode value, saved across recompositions
	var barcode by rememberSaveable { mutableStateOf<String?>("No Code Scanned") }

	// State to manage the camera permission
	val permissionState = rememberPermissionState(
		Manifest.permission.CAMERA, // Permission being requested
	                                             )

	// State to track whether to show the rationale dialog for the permission
	var onCancel by remember(permissionState.status.shouldShowRationale) {
		mutableStateOf(permissionState.status.shouldShowRationale)
	}

	// Check if a barcode has been scanned
	if (barcode != null) {

		Box(
			modifier = Modifier
				.fillMaxSize()
		   ) {
			Column(
				modifier = Modifier
					.align(Alignment.Center)
					.padding(16.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center,
			      ) {				// Show rationale dialog if permission is denied and rationale can be shown
				if (onCancel) {
					ShowRationaleDialog(
						onDismiss = { onCancel = false },
						onConfirm = { permissionState.launchPermissionRequest() },
						body = permissionState.permission,
					                   )
				}

				// Determine the text to show based on the permission state
				val textToShow =
					if (permissionState.status.shouldShowRationale) {					// If the user has denied the permission but the rationale can be shown,
						// explain why the app requires this permission
						"The Camera permission is important for this app. Please grant the permission."
					}
					else if (!permissionState.status.isGranted) {					// If it's the first time the user lands on this feature, or the user
						// doesn't want to be asked again for this permission, explain that the
						// permission is required
						"Camera permission required for this feature to be available. " + "Please grant the permission"
					}
					else {					// If permission is granted, show the scanned barcode or a default message
						barcode ?: "No Scanned"
					}

				// Display the determined text
				Text(textToShow)

				// Show a button to scan a QR or barcode if permission is granted
				if (permissionState.status.isGranted) {
					Button(onClick = { barcode = null }) {
						Text("Scan QR or Barcode")
					}
				}
				else {					// Show a button to request camera permission if not granted
					Button(onClick = { permissionState.launchPermissionRequest() }) {
						Text("Request permission")
					}
				}
			}
		}
	}
	else {		// If no barcode has been scanned, show the QR/barcode scanner
		ScanCode(
			onCodeDetected = {
				Timber.d("Barcode detected: $it")
				barcode = it // Update the barcode state with the scanned value
			},
		        )
	}
}
