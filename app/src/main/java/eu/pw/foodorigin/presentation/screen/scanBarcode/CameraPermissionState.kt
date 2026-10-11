package eu.pw.foodorigin.presentation.screen.scanBarcode

internal enum class CameraPermissionScreenState {
	Request,
	Rationale,
	Settings,
}

internal fun cameraPermissionScreenState(
	hasRequestedPermission: Boolean,
	shouldShowRationale: Boolean,
): CameraPermissionScreenState = when {
	shouldShowRationale -> CameraPermissionScreenState.Rationale
	!hasRequestedPermission -> CameraPermissionScreenState.Request
	else -> CameraPermissionScreenState.Settings
}
