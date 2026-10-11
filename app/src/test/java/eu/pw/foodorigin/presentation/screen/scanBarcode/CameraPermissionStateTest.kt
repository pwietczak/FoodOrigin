package eu.pw.foodorigin.presentation.screen.scanBarcode

import org.junit.Assert.assertEquals
import org.junit.Test

class CameraPermissionStateTest {

	@Test
	fun `initial permission state requests access`() {
		assertEquals(
			CameraPermissionScreenState.Request,
			cameraPermissionScreenState(
				hasRequestedPermission = false,
				shouldShowRationale = false,
			),
		)
	}

	@Test
	fun `denied permission with rationale allows retry`() {
		assertEquals(
			CameraPermissionScreenState.Rationale,
			cameraPermissionScreenState(
				hasRequestedPermission = false,
				shouldShowRationale = true,
			),
		)
	}

	@Test
	fun `denied permission without rationale opens settings`() {
		assertEquals(
			CameraPermissionScreenState.Settings,
			cameraPermissionScreenState(
				hasRequestedPermission = true,
				shouldShowRationale = false,
			),
		)
	}
}
