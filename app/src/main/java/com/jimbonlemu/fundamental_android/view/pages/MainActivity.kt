package com.jimbonlemu.fundamental_android.view.pages

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.fundamental_android.data.response.UserItem
import com.jimbonlemu.fundamental_android.databinding.ActivityMainBinding
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

        mainViewModel.searchResult.observe(this) { listData ->
            if (listData != null) {
                setListGithubUserData(listData)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoader(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                mainViewModel.searchGithubUser(searchView.text.toString())
                false
            }
        }

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.layoutManager = layoutManager
        val itemDecor = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithubUser.addItemDecoration(itemDecor)
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
