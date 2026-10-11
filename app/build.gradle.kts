plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "eu.pw.foodorigin"
	compileSdk = 36

	defaultConfig {
		applicationId = "eu.pw.foodorigin"
		minSdk = 28
		targetSdk = 36
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		debug {
			isMinifyEnabled = false
			buildConfigField("String", "BASE_OPEN_FOOD_FACTS_API_URL", "\"https://world.openfoodfacts.org/api/v2/\"")
		}
		release {
			isMinifyEnabled = true
			buildConfigField("String", "BASE_OPEN_FOOD_FACTS_API_URL", "\"https://world.openfoodfacts.org/api/v2/\"")
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
			             )
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.bundles.compose)
	implementation(libs.androidx.navigation.compose)

	implementation(libs.timber)
	implementation(libs.bundles.koin)
	implementation(libs.bundles.ktor)
	implementation(libs.bundles.barcodeScanning)
	implementation(libs.bundles.cameraX)
	implementation(libs.bundles.coil)

	testImplementation(libs.bundles.unitTests)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	debugImplementation(libs.androidx.compose.ui.tooling)
	debugImplementation(libs.androidx.compose.ui.test.manifest)
}