package com.jimbonlemu.fundamental_android.di

import android.content.Context
import com.jimbonlemu.fundamental_android.data.local.room.FavoriteDatabase
import com.jimbonlemu.fundamental_android.data.repository.FavoriteRepository

object Injection {
    fun provideRepositoryOfFavorite(context: Context): FavoriteRepository {
        return FavoriteRepository.getInstance(FavoriteDatabase.getDatabase(context).favoriteDao())
    }
}
