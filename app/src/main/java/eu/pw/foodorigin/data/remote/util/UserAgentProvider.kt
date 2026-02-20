package eu.pw.foodorigin.data.remote.util

import android.content.Context
import eu.pw.foodorigin.BuildConfig
import eu.pw.foodorigin.R

class UserAgentProvider(private val context: Context) {

	/**
	 * Generates a User-Agent string containing the application name, platform, version, and project URL.
	 *
	 * This identifies the client to the API as per the Open Food Facts requirements.
	 *
	 * https://openfoodfacts.github.io/openfoodfacts-server/api/tutorials/scanning-barcodes/#step-33-make-the-api-call
	 *
	 * @return A formatted string representing the User-Agent.
	 */
	operator fun invoke(): String {
		val appName = context.getString(R.string.app_name)
		val appType = "Android"
		val versionName = BuildConfig.VERSION_NAME
		val contactUrl = "https://github.com/pwietczak/FoodOrigin"

		return "$appName - $appType - Version $versionName - $contactUrl"
	}
}