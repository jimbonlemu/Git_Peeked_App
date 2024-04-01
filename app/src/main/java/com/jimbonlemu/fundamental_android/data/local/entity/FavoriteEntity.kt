package com.jimbonlemu.fundamental_android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val userImage: String? = null
)
