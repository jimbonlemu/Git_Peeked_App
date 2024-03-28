package com.jimbonlemu.fundamental_android.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val userImage: String? = null
):Parcelable
