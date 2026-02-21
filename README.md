# Food Origin
**Food Origin** is an Android application that allows users to instantly check the origin and detailed information of food products by scanning barcodes. The project is powered by the **Open Food Facts** open database.

[![Android CI](https://github.com/pwietczak/FoodOrigin/actions/workflows/android_ci.yml/badge.svg)](https://github.com/pwietczak/FoodOrigin/actions/workflows/android_ci.yml)
![Branch](https://img.shields.io/badge/branch-develop-blue)

##  Features

- **Barcode Scanning**: Utilizes CameraX and Google ML Kit for fast EAN code recognition.
- **Product Details**: Displays name, brands, and ingredients.
- **Open Food Facts Integration**: Access to a massive, community-driven food database.
- **Modern UI**: Fully built with Jetpack Compose following Material Design 3 guidelines.

##  Tech Stack

The application is built using modern Android development standards:

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Navigation**: [Navigation Compose](https://developer.android.com/develop/ui/compose/navigation)
- **Architecture**: Clean Architecture with `domain`, `data`, and `presentation` layers.
- **Dependency Injection**: [Koin](https://insert-koin.io/)
- **Networking**: [Ktor Client](https://ktor.io/docs/client-dependencies.html) (with Kotlinx Serialization)
- **Camera Support**: [CameraX](https://developer.android.com/training/camerax)
- **Scanning**: [Google ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning)
- **Asynchrony**: Kotlin Coroutines & Flow
- **Logging**: [Timber](https://github.com/JakeWharton/timber)
- **Image Loading**: [Coli](https://github.com/coil-kt/coil)

## Project Structure

The project is divided into logical packages:
- `data` – repository implementations, API handling (Ktor), and data mappers.
- `domain` – business models and repository interfaces.
- `presentation` – UI layer, ViewModels, and navigation (Compose Navigation).
- `di` – dependency injection modules.

##  Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/pwietczak/FoodOrigin.git
   ```
2. Open the project in **Android Studio (Ladybug or newer)**.
3. Sync the project with Gradle files.
4. Run the app on a physical device (required for scanner testing) or an emulator.

## CI/CD

The project includes a **GitHub Actions** workflow that automatically:
- Builds the Release version.
- Runs unit tests on every push or pull request to the `develop` branch.
