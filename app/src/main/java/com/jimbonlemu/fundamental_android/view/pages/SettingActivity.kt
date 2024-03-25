package com.jimbonlemu.fundamental_android.view.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.jimbonlemu.fundamental_android.databinding.ActivitySettingBinding
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import com.jimbonlemu.fundamental_android.utils.dataStore
import com.jimbonlemu.fundamental_android.view.view_model.SettingViewModel
import com.jimbonlemu.fundamental_android.view.view_model.SettingViewModelFactory


class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var settingViewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = SettingPreference.getInstance(application.dataStore)
        settingViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        with(settingViewModel) {
            with(binding.btnSwitch) {
                getThemeSetting().observe(this@SettingActivity) { darkModeIsActive ->
                    val setDarkMode = if (darkModeIsActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                    isChecked = darkModeIsActive
                    AppCompatDelegate.setDefaultNightMode(setDarkMode)
                }

                setOnCheckedChangeListener { _, isChecked ->
                    saveThemeSetting(isChecked)

                }
            }
        }
    }
}
