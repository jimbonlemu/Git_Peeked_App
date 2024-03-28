package com.jimbonlemu.fundamental_android.view.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.fundamental_android.data.local.entity.FavoriteEntity
import com.jimbonlemu.fundamental_android.data.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    fun getListAllFavorite() = favoriteRepository.getAllFavorite()

    fun checkStatusFavorite(username:String) = favoriteRepository.checkStatusFavorite(username)

    fun insertFavorite(favoriteEntity: FavoriteEntity){
        viewModelScope.launch {
            favoriteRepository.insertFavorite(favoriteEntity)
        }
    }

    fun deleteFavorite(favoriteEntity: FavoriteEntity){
        viewModelScope.launch {
            favoriteRepository.removeFavorite(favoriteEntity)
        }
    }
}
