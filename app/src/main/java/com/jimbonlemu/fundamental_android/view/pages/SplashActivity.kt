package com.jimbonlemu.fundamental_android.view.pages

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.jimbonlemu.fundamental_android.R
import com.jimbonlemu.fundamental_android.databinding.ActivitySplashBinding
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import com.jimbonlemu.fundamental_android.utils.dataStore
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivitySplashBinding.inflate(layoutInflater).apply {
            setContentView(root)
            initDarkMode()

            ivSplashScreen.startAnimation(
                AnimationUtils.loadAnimation(
                    this@SplashActivity, R.anim.anim_fade_in_to_out
                )
            )

            lifecycleScope.launch {
                delay(SPLASH_SCREEN_DURATION.seconds)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }

    }

    private fun initDarkMode() {
        SettingPreference.getInstance(application.dataStore).getThemeSetting().asLiveData()
            .observe(this) { darkModeActive ->
                val setMode =
                    if (darkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(setMode)
                ActivitySplashBinding.inflate(layoutInflater).setLogoMode(darkModeActive)
            }
    }

    private fun ActivitySplashBinding.setLogoMode(isDarkMode: Boolean) {
        with(ivSplashScreen) {
            if (isDarkMode) {
                setImageResource(R.drawable.app_logo_dark_mode)
            } else {
                setImageResource(R.drawable.app_logo)
            }
        }
    }

    companion object {
        const val SPLASH_SCREEN_DURATION = 3
    }
}
