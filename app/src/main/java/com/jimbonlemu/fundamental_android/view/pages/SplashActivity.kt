package com.jimbonlemu.fundamental_android.view.pages

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jimbonlemu.fundamental_android.R
import com.jimbonlemu.fundamental_android.databinding.ActivitySplashBinding
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import com.jimbonlemu.fundamental_android.utils.dataStore
import com.jimbonlemu.fundamental_android.view.view_model.SettingViewModel
import com.jimbonlemu.fundamental_android.view.view_model.SettingViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var settingViewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDarkMode()

        binding.ivSplashScreen.startAnimation(
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

    private fun initDarkMode() {
        settingViewModel =
            ViewModelProvider(
                this,
                SettingViewModelFactory(SettingPreference.getInstance(application.dataStore))
            )[SettingViewModel::class.java]
        with(settingViewModel) {

            getThemeSetting().observe(this@SplashActivity) { darkModeActive ->
                val setMode =
                    if (darkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(setMode)
            }
        }
    }

    companion object {
        const val SPLASH_SCREEN_DURATION = 2
    }
}
