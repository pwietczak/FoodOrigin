package eu.pw.foodorigin.data.remote.util

/**
 * Ensures that the given [url] starts with a valid protocol (http or https).
 * If it doesn't, it prepends "https://" to it.
 * Also handles trimming and null/blank checks.
 */
fun formatUrl(url: String?): String? {
    val trimmed = url?.trim()
    if (trimmed.isNullOrBlank()) return null
    
    return if (!trimmed.startsWith("http://", ignoreCase = true) && 
               !trimmed.startsWith("https://", ignoreCase = true)) {
        "https://$trimmed"
    } else {
        trimmed
    }
}
