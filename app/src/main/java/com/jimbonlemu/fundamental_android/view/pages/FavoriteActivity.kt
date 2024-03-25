package com.jimbonlemu.fundamental_android.view.pages

import android.os.Bundle
import com.jimbonlemu.fundamental_android.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppBarActivity("Favorites Page") {

    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}