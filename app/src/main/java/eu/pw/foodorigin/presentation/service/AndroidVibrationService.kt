package eu.pw.foodorigin.presentation.service

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import eu.pw.foodorigin.domain.service.HapticFeedbackService

class AndroidVibrationService(context: Context) : HapticFeedbackService {
	private val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
	private val effect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
	override fun vibrate(){
		vibrator?.vibrate(effect)
	}
}