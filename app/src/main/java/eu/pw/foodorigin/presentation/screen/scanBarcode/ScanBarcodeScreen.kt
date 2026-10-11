package eu.pw.foodorigin.presentation.screen.scanBarcode

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.PreviewView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.foodorigin.presentation.screen.scanBarcode.components.CameraPermissionContent
import eu.pw.foodorigin.presentation.screen.scanBarcode.components.BarcodeOverlay
import eu.pw.foodorigin.presentation.screen.scanBarcode.components.PreviewOverlay
import eu.pw.foodorigin.presentation.screen.scanBarcode.model.BarcodeBoundingBox
import eu.pw.foodorigin.presentation.screen.scanBarcode.model.DetectedBarcode
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.RegionOfInterestConfig.isCenterInsideROI
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.getBarcodeScanner
import eu.pw.foodorigin.presentation.screen.scanBarcode.utils.getCameraController

@Composable
fun ScanBarcodeScreen(
	modifier: Modifier = Modifier,
	viewModel: ScanBarcodeViewModel,
	onBarcodeScanned: (String) -> Unit,
	onPermissionExit: () -> Unit,
	onExit: () -> Unit,
                     ) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current
	var hasCameraPermission by rememberSaveable {
		mutableStateOf(
			ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
				PackageManager.PERMISSION_GRANTED,
		)
	}
	var hasRequestedCameraPermission by rememberSaveable {
		mutableStateOf(hasCameraPermission)
	}
	val permissionLauncher = rememberLauncherForActivityResult(
		ActivityResultContracts.RequestPermission(),
	) { granted ->
		hasRequestedCameraPermission = true
		hasCameraPermission = granted
	}
	val activity = remember(context) { context.findActivity() }

	DisposableEffect(lifecycleOwner, context) {
		val observer = LifecycleEventObserver { _, event ->
			if (event == Lifecycle.Event.ON_RESUME) {
				hasCameraPermission =
					ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
						PackageManager.PERMISSION_GRANTED
			}
		}
		lifecycleOwner.lifecycle.addObserver(observer)
		onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
	}

	LaunchedEffect(Unit) {
		viewModel.events.collect {
			when (it) {
				is ScanBarcodeEvent.BarcodeScanned -> {
					onBarcodeScanned(it.code)
				}
			}
		}
	}

	if (hasCameraPermission) {
		ScanBarcodeScreenContent(
			modifier = modifier,
			uiState = uiState,
			onBarcodeDetected = viewModel::onCodeDetected,
			onExit = onExit,
		)
	} else {
		val shouldShowRationale =
			activity?.let {
				ActivityCompat.shouldShowRequestPermissionRationale(it, Manifest.permission.CAMERA)
			} ?: false
		val permissionScreenState = cameraPermissionScreenState(
			hasRequestedPermission = hasRequestedCameraPermission,
			shouldShowRationale = shouldShowRationale,
		)

		CameraPermissionContent(
			modifier = modifier,
			state = permissionScreenState,
			onRequestPermission = { permissionLauncher.launch(Manifest.permission.CAMERA) },
			onOpenSettings = {
				context.startActivity(
					Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
						data = Uri.fromParts("package", context.packageName, null)
					},
				)
			},
			onExit = onPermissionExit,
		)
	}
}

@Composable
fun ScanBarcodeScreenContent(
	modifier: Modifier,
	uiState: ScanBarcodeUiState,
	onBarcodeDetected: (DetectedBarcode) -> Unit,
	onExit: () -> Unit,
                            ) {
	val context = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current

	val barcodeScanner = remember { getBarcodeScanner() }
	val cameraController = remember {
		getCameraController(context).apply {
			bindToLifecycle(lifecycleOwner)
		}
	}

	Box(modifier = Modifier.fillMaxSize()) {
		AndroidView(
			modifier = Modifier.fillMaxSize(),
			factory = { ctx ->
				PreviewView(ctx).apply {
					scaleType = PreviewView.ScaleType.FILL_CENTER
					controller = cameraController
					cameraController.setImageAnalysisAnalyzer(
						ContextCompat.getMainExecutor(context),
						MlKitAnalyzer(
							listOf(barcodeScanner),
							COORDINATE_SYSTEM_VIEW_REFERENCED,
							ContextCompat.getMainExecutor(context),
						             ) { result: MlKitAnalyzer.Result? ->
							val barcode = result?.getValue(barcodeScanner)?.firstOrNull()
							val boundingBox = barcode?.boundingBox
							val value = barcode?.rawValue
							if (value != null && boundingBox != null) {
								if (boundingBox.isCenterInsideROI(
										width.toFloat(), height.toFloat(),
								                                 )
								) onBarcodeDetected(
									DetectedBarcode(
										code = value,
										boundingBox = BarcodeBoundingBox(
											left = boundingBox.left.toFloat(),
											top = boundingBox.top.toFloat(),
											right = boundingBox.right.toFloat(),
											bottom = boundingBox.bottom.toFloat(),
										                                ),
									               ),
								                   )
							}
						},
					                                         )
				}
			},
		           )
		PreviewOverlay(onExit = onExit)
		uiState.detectedBarcode?.let {
			BarcodeOverlay(modifier = Modifier, barcode = it)
		}
	}

	DisposableEffect(lifecycleOwner) {
		onDispose {
			cameraController.clearImageAnalysisAnalyzer()
			cameraController.unbind()
			barcodeScanner.close()
		}
	}
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
	is Activity -> this
	is ContextWrapper -> baseContext.findActivity()
	else -> null
}
