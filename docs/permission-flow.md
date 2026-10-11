# Camera Permission Flow

## Purpose

FoodOrigin uses the camera only to scan product barcodes. Camera access is optional: users can enter a barcode manually without granting camera permission.

## Where permission is requested

The app opens directly on the product-code input screen. Opening the scanner navigates to `ScanBarcodeScreen`, which checks the camera permission and requests it there if needed.

The permission is managed by `ScanBarcodeScreen` using AndroidX's `ActivityResultContracts.RequestPermission`. CameraX is only initialized when camera permission is granted.

## Flow

1. **Open the app**
   - `MainActivity` displays `ProductNavHost`.
   - The navigation start destination is `ProductCodeInput`.
   - No camera permission is requested at startup.

2. **Open the scanner**
   - The user selects the scan button on the product-code input screen.
   - The app navigates to `ScanBarcodeScreen`.
   - The screen checks `Manifest.permission.CAMERA`.

3. **Permission already granted**
   - The scanner content is displayed.
   - CameraX binds the camera controller to the current lifecycle and starts barcode analysis.

4. **Permission not granted**
   - The scanner displays a camera-permission explanation and a manual-entry option.
   - On the first request, the user can choose **Allow camera** to launch the Android permission dialog.
   - The app does not create or bind CameraX while permission is missing.

5. **User denies the request**
   - The permission result is stored in saveable Compose state.
   - If Android recommends showing a rationale, the screen explains why camera access is needed and offers **Try again**.
   - If Android does not recommend a rationale after a request, the screen offers **Open settings**. The user can also leave the scanner and enter the barcode manually.

6. **Return from system settings**
   - The screen checks the permission again when its lifecycle receives `ON_RESUME`.
   - If permission was granted in settings, the scanner content is displayed.
   - If permission is still denied, the permission explanation and settings option remain visible.

7. **Exit without granting permission**
   - Selecting **Exit** on the permission screen returns to the product-code input screen.
   - The barcode field receives focus and the software keyboard opens so the user can enter a code manually.
   - This focus behavior applies only to exiting the permission screen; returning with a scanned barcode follows the separate scan-result flow.

## Permission-state selection

`cameraPermissionScreenState` maps the request history and Android rationale result to the screen shown:

| Request has been made | Android recommends rationale | Screen state |
|---|---|---|
| No | No | Initial request |
| Either | Yes | Rationale and retry |
| Yes | No | Settings guidance |

The rationale check uses `ActivityCompat.shouldShowRequestPermissionRationale`. `Context.findActivity()` unwraps `ContextWrapper` instances to obtain the `Activity` required by that API.

## Camera lifecycle

`ScanBarcodeScreenContent` is only composed after permission is granted. When that content leaves composition, its `DisposableEffect` clears the image-analysis analyzer, unbinds the camera controller, and closes the barcode scanner.

## Manifest and dependency

- `AndroidManifest.xml` declares `android.permission.CAMERA`.
- The camera hardware feature is marked `required="false"`, so devices without a camera can still install the app and use manual barcode entry.
- Permission handling uses AndroidX Activity Result APIs. Accompanist Permissions is not required.

## Relevant source files

- `app/src/main/java/eu/pw/foodorigin/MainActivity.kt`
- `app/src/main/java/eu/pw/foodorigin/presentation/navigation/ProductNavHost.kt`
- `app/src/main/java/eu/pw/foodorigin/presentation/screen/productcodeinput/ProductCodeInputScreen.kt`
- `app/src/main/java/eu/pw/foodorigin/presentation/screen/scanBarcode/ScanBarcodeScreen.kt`
- `app/src/main/java/eu/pw/foodorigin/presentation/screen/scanBarcode/CameraPermissionState.kt`
- `app/src/main/java/eu/pw/foodorigin/presentation/screen/scanBarcode/components/CameraPermissionContent.kt`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/res/values/strings.xml`

## Tests

`CameraPermissionStateTest` covers the initial request, rationale/retry state, and settings state.