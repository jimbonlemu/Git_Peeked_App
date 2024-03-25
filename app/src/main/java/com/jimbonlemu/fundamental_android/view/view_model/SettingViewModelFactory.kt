package com.jimbonlemu.fundamental_android.view.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.jimbonlemu.fundamental_android.utils.SettingPreference

@Suppress("UNCHECKED_CAST")
class SettingViewModelFactory (private val pref:SettingPreference):NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }

}