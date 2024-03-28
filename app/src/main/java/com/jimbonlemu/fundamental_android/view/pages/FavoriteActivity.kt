package com.jimbonlemu.fundamental_android.view.pages

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.fundamental_android.data.local.entity.FavoriteEntity
import com.jimbonlemu.fundamental_android.data.remote.response.UserItem
import com.jimbonlemu.fundamental_android.databinding.ActivityFavoriteBinding
import com.jimbonlemu.fundamental_android.view.adapter.ListGithubUserAdapter
import com.jimbonlemu.fundamental_android.view.view_model.FavoriteViewModel
import com.jimbonlemu.fundamental_android.view.view_model_factory.FavoriteViewModelFactory
class FavoriteActivity : AppBarActivity("Favorites Page") {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListGithubUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        val favViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val favViewModel: FavoriteViewModel by viewModels {
            favViewModelFactory
        }

        favViewModel.getListAllFavorite().observe(this) { value ->
            setupAdapter(value)
            if (value.isNullOrEmpty()) {
                Toast.makeText(this, "You don\'t favorite any users at all", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initRecyclerView() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun setupAdapter(listFavorites: List<FavoriteEntity>) {
        val userItems = listFavorites.map { favoriteEntity ->
            UserItem(login = favoriteEntity.username, avatarUrl = favoriteEntity.userImage)
        }

        if (!::adapter.isInitialized) {
            adapter = ListGithubUserAdapter()
            binding.rvFavorite.adapter = adapter
        }
        adapter.submitList(userItems)
    }
}
