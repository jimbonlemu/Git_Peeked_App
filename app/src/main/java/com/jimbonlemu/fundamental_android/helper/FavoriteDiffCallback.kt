package com.jimbonlemu.fundamental_android.helper

import androidx.recyclerview.widget.DiffUtil
import com.jimbonlemu.fundamental_android.data.local.entity.FavoriteEntity

class FavoriteDiffCallback(private val oldFavorite:List<FavoriteEntity>, private val newFavorite:List<FavoriteEntity>):DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavorite.size

    override fun getNewListSize(): Int  = newFavorite.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavorite[oldItemPosition].userName == newFavorite[newItemPosition].userName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavorite[oldItemPosition]
        val newFav = newFavorite[newItemPosition]
        return oldFav.userName == newFav.userName && oldFav.userImage  == newFav.userImage
    }
}
