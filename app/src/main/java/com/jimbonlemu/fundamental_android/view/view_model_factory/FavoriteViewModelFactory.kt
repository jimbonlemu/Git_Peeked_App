package com.jimbonlemu.fundamental_android.view.view_model_factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jimbonlemu.fundamental_android.data.repository.FavoriteRepository
import com.jimbonlemu.fundamental_android.di.Injection
import com.jimbonlemu.fundamental_android.view.view_model.FavoriteViewModel

class FavoriteViewModelFactory (private val favRepo: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null
        fun getInstance(context: Context): FavoriteViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(Injection.provideRepositoryOfFavorite(context))
            }.also { instance = it }
    }
}