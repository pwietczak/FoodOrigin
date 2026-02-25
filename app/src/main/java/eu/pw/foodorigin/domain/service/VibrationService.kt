package eu.pw.foodorigin.domain.service

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService

class VibrationService(context: Context) {
	private val vibrator = getSystemService(context, Vibrator::class.java)

	fun vibrate(effect: VibrationEffect){
		vibrator?.vibrate(effect)
	}
}
