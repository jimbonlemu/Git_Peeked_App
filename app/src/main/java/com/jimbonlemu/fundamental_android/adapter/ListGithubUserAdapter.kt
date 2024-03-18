package com.jimbonlemu.fundamental_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimbonlemu.fundamental_android.data.response.UserItem
import com.jimbonlemu.fundamental_android.databinding.UserWidgetBinding


class ListGithubUserAdapter :
    ListAdapter<UserItem, ListGithubUserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(val binding: UserWidgetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: UserItem) {
            Glide.with(binding.root).load(userItem.avatarUrl).into(binding.civUserImage)
            binding.tvUserGithubName.text = userItem.login
            binding.tvGithubUserFollowers.text = userItem.followersCount.toString()
            binding.tvGithubUserFollowing.text = userItem.followingCount.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            UserWidgetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val gitUser = getItem(position)
        holder.bind(gitUser)

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
                oldItem == newItem
        }
    }

}
