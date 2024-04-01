package com.jimbonlemu.fundamental_android.view.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.asLiveData
import com.jimbonlemu.git_peeked.R
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import com.jimbonlemu.fundamental_android.utils.dataStore

open class AppBarActivity(private val appBarTitle: String) : AppCompatActivity() {

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
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
            title = appBarTitle
            elevation = 0f
        }
        setIconMode()
    }

    private fun setIconMode() {
        SettingPreference.getInstance(application.dataStore).getThemeSetting().asLiveData()
            .observe(this) { darkModeIsActive ->
                val isBackIconDarkMode =
                    if (darkModeIsActive) R.drawable.icon_back_dark_mode else R.drawable.icon_back
                supportActionBar?.setHomeAsUpIndicator(isBackIconDarkMode)
            }
    }
}
