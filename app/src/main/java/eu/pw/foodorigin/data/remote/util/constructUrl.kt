package eu.pw.foodorigin.data.remote.util

import eu.pw.foodorigin.BuildConfig

fun constructUrl(url:String): String =
	when {
		url.contains(BuildConfig.BASE_OPEN_FOOD_FACTS_API_URL) -> url
		url.startsWith("/") -> BuildConfig.BASE_OPEN_FOOD_FACTS_API_URL + url.drop(1)
		else -> BuildConfig.BASE_OPEN_FOOD_FACTS_API_URL + url
	}
