package com.jimbonlemu.fundamental_android.view.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.git_peeked.R
import com.jimbonlemu.fundamental_android.data.remote.response.UserItem
import com.jimbonlemu.git_peeked.databinding.ActivityMainBinding
import com.jimbonlemu.fundamental_android.view.adapter.ListGithubUserAdapter
import com.jimbonlemu.fundamental_android.view.view_model.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        with(mainViewModel) {
            isError.observe(this@MainActivity) {
                binding.tvMainErrorText.text = it
            }

            searchResult.observe(this@MainActivity) { listData ->
                if (listData != null) {
                    setListGithubUserData(listData)
                }
            }

            isLoading.observe(this@MainActivity) {
                showLoader(it)
            }

        }

        setupAppBar()
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val searchText = searchView.text.toString().trim()
                    if (searchText.isNotEmpty()) {
                        searchBar.setText(searchText)
                        searchView.hide()
                        mainViewModel.searchGithubUser(searchText)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Please type the username to search",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }

    }

    private fun setupAppBar() {
        binding.abToolbar.setOnMenuItemClickListener { itemClicked ->
            when (itemClicked.itemId) {
                R.id.menuToSetting -> {
                    startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                    true
                }

                R.id.menuToFavorite -> {
                    startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvGithubUser.apply {
            val layoutManager = LinearLayoutManager(this@MainActivity)
            this.layoutManager = layoutManager
            val itemDecor = DividerItemDecoration(this@MainActivity, layoutManager.orientation)
            addItemDecoration(itemDecor)
        }
    }

    private fun setListGithubUserData(users: List<UserItem>) {
        val adapter = ListGithubUserAdapter()
        adapter.submitList(users)
        binding.rvGithubUser.adapter = adapter
    }

    private fun showLoader(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                mainShimmerLoader.root.startShimmer()
                mainShimmerLoader.root.visibility = View.VISIBLE
                rvGithubUser.visibility = View.INVISIBLE
            } else {
                mainShimmerLoader.root.stopShimmer()
                mainShimmerLoader.root.visibility = View.GONE
                rvGithubUser.visibility = View.VISIBLE
            }
        }
    }
}
