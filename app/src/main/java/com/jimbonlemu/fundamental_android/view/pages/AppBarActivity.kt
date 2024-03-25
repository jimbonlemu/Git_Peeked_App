package com.jimbonlemu.fundamental_android.view.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.jimbonlemu.fundamental_android.R
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import com.jimbonlemu.fundamental_android.utils.dataStore
import com.jimbonlemu.fundamental_android.view.view_model.SettingViewModel
import com.jimbonlemu.fundamental_android.view.view_model.SettingViewModelFactory

open class AppBarActivity(private val appBarTitle: String) : AppCompatActivity() {
    private lateinit var settingViewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppBar(appBarTitle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setAppBar(appBarTitle: String) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = appBarTitle
        setIconMode()
    }

    private fun setIconMode() {
        settingViewModel = ViewModelProvider(this, SettingViewModelFactory(SettingPreference.getInstance(application.dataStore)))[SettingViewModel::class.java]
        settingViewModel.getThemeSetting().observe(this) { darkModeIsActive ->
            val isBackIconDarkMode = if (darkModeIsActive) R.drawable.icon_back_dark_mode else R.drawable.icon_back
            supportActionBar?.setHomeAsUpIndicator(isBackIconDarkMode)
        }
    }
}
