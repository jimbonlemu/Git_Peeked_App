package com.jimbonlemu.fundamental_android.view.pages

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.fundamental_android.data.remote.response.UserItem
import com.jimbonlemu.fundamental_android.databinding.ActivityFavoriteBinding
import com.jimbonlemu.fundamental_android.view.adapter.ListGithubUserAdapter
import com.jimbonlemu.fundamental_android.view.view_model.FavoriteViewModel
import com.jimbonlemu.fundamental_android.view.view_model_factory.FavoriteViewModelFactory

class FavoriteActivity : AppBarActivity("Favorites Page") {

    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecylcerView()

        val favViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val favViewModel: FavoriteViewModel by viewModels {
            favViewModelFactory
        }

        favViewModel.getListAllFavorite().observe(this){value ->
            val listFavorites = arrayListOf<UserItem>()
            value.map {data ->
                val item = UserItem(login = data.username, avatarUrl = data.userImage)
                listFavorites.add(item)
            }
           setupAdapter(listFavorites)
        }
    }

    private fun initRecylcerView(){
        with(binding.rvFavorite){
            val layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this.layoutManager = layoutManager
            val itemDecor = DividerItemDecoration(this@FavoriteActivity, layoutManager.orientation)
            addItemDecoration(itemDecor)
        }
    }

    private fun setupAdapter(listFavorites:ArrayList<UserItem>){
        val adapter = ListGithubUserAdapter()
        adapter.submitList(listFavorites)
        binding.rvFavorite.adapter = adapter
    }
}
