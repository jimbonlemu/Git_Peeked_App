package com.jimbonlemu.fundamental_android.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jimbonlemu.fundamental_android.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(data: FavoriteEntity)
    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun checkFavoriteStatus(username: String): LiveData<FavoriteEntity>
    @Delete
    suspend fun delete(data: FavoriteEntity)
    @Query("SELECT * FROM FavoriteEntity")
    fun getAllFavorite():LiveData<List<FavoriteEntity>>

}
