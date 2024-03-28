package com.jimbonlemu.fundamental_android.data.repository

import androidx.lifecycle.LiveData
import com.jimbonlemu.fundamental_android.data.local.entity.FavoriteEntity
import com.jimbonlemu.fundamental_android.data.local.room.FavoriteDao

class FavoriteRepository private constructor(
    private val mFavDao: FavoriteDao
) {

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavDao.getAllFavorite()

    suspend fun removeFavorite(favData: FavoriteEntity) = mFavDao.delete(favData)

    fun checkStatusFavorite(username:String):LiveData<FavoriteEntity> = mFavDao.checkFavoriteStatus(username)

    suspend fun insertFavorite(favData: FavoriteEntity) = mFavDao.insertFavorite(favData)

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(favDao: FavoriteDao): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favDao).also { instance = it }
            }
    }
}
