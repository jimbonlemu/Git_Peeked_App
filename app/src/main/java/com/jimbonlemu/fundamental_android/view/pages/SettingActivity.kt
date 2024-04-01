package com.jimbonlemu.fundamental_android.view.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.jimbonlemu.git_peeked.databinding.ActivitySettingBinding
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import com.jimbonlemu.fundamental_android.utils.dataStore
import com.jimbonlemu.fundamental_android.view.view_model.SettingViewModel
import com.jimbonlemu.fundamental_android.view.view_model_factory.SettingViewModelFactory

class SettingActivity : AppBarActivity("Setting Page") {
    private lateinit var settingViewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivitySettingBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setupViewModel()
            setupSwitchButton()
        }
    }

    private fun setupViewModel() {
        settingViewModel =
            ViewModelProvider(
                this,
                SettingViewModelFactory(SettingPreference.getInstance(application.dataStore))
            )[SettingViewModel::class.java]
    }

    private fun ActivitySettingBinding.setupSwitchButton() {
        with(settingViewModel) {
            with(btnSwitch) {
                getThemeSetting().observe(this@SettingActivity) { darkModeIsActive ->
                    val setDarkMode =
                        if (darkModeIsActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
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
