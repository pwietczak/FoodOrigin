import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	alias(libs.plugins.android.application) apply false
	alias(libs.plugins.kotlin.compose) apply false
	alias(libs.plugins.kotlin.serialization) apply false
}

allprojects {
	tasks.withType(Test::class).configureEach {
		testLogging {
			events = setOf(
				TestLogEvent.STARTED,
				TestLogEvent.FAILED,
				TestLogEvent.SKIPPED,
				TestLogEvent.PASSED
						  )

			exceptionFormat = TestExceptionFormat.FULL
			showExceptions = true
			showCauses = true
			showStackTraces = true
		}
	}
}