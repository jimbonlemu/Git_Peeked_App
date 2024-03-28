package com.jimbonlemu.fundamental_android.view.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val pref:SettingPreference):ViewModel() {

    fun getThemeSetting():LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive:Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
