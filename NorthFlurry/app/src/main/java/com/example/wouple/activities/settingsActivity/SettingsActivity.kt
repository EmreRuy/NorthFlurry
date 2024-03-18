package com.example.wouple.activities.settingsActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wouple.model.api.TemperatureResponse

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsView(
                onBackPressed = { onBackPressedDispatcher.onBackPressed() },
                onFeedbackClicked = { trouble ->
                    sendEmail(trouble)
                }
            )
        }
    }
    private fun sendEmail(isProblem: Boolean) {
        val selectorIntent = Intent(Intent.ACTION_SENDTO)
        selectorIntent.data = Uri.parse("mailto:")

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf("uyar.em.eu@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, if (isProblem) "Trouble with the app" else "have an idea")
            putExtra(
                Intent.EXTRA_TEXT, """
                
                
                ––––––––––––––––––
                Device name: ${android.os.Build.MODEL}
                OS version: ${android.os.Build.VERSION.RELEASE}""".trimIndent()
            )
        }
        emailIntent.selector = selectorIntent
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
}
