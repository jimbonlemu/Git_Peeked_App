package com.jimbonlemu.fundamental_android.view.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimbonlemu.fundamental_android.data.remote.response.UserItem
import com.jimbonlemu.fundamental_android.databinding.UserWidgetBinding
import com.jimbonlemu.fundamental_android.view.pages.DetailActivity

class ListGithubUserAdapter :
    ListAdapter<UserItem, ListGithubUserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(private val binding: UserWidgetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: UserItem) {
            with(binding) {
                val args = Bundle()
                args.putString("username", userItem.login)

                Glide.with(root).load(userItem.avatarUrl).into(civUserImage)

                tvUserGithubName.text = userItem.login

                root.setOnClickListener {
                    it.context.startActivity(
                        Intent(
                            it.context,
                            DetailActivity::class.java
                        ).putExtras(args)
                    )
                }
            }
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
