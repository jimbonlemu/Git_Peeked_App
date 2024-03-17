package com.jimbonlemu.fundamental_android.pages

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.fundamental_android.adapter.ListGithubUserAdapter
import com.jimbonlemu.fundamental_android.data.api.ApiConfig
import com.jimbonlemu.fundamental_android.data.models.SearchResponse
import com.jimbonlemu.fundamental_android.data.models.UserItem
import com.jimbonlemu.fundamental_android.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.layoutManager = layoutManager
        binding.rvGithubUser.addItemDecoration(
            DividerItemDecoration(
                this,
                layoutManager.orientation
            )
        )

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
                searchBar.setText(searchView.text)
                searchView.hide()
                getUser(username = searchView.text.toString())
                false
            }
        }

    }

    private fun getUser(username: String? = null) {
        val client = ApiConfig.connectApiService().searchGithubUser(username ?: "jimbonlemu")
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (response.body() != null) {
                        setUser(responseBody?.items)
                    }
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
    }


    private fun setUser(userItem: List<UserItem?>?) {
        val adapter = ListGithubUserAdapter()
        userItem?.forEach { user ->
            user?.let {
                user.followersUrl?.let { followersUrl ->
                    ApiConfig.connectApiService().getFollowers(followersUrl)
                        .enqueue(object : Callback<List<UserItem>> {
                            override fun onResponse(
                                call: Call<List<UserItem>>,
                                response: Response<List<UserItem>>
                            ) {
                                if (response.isSuccessful) {
                                    user.followersCount = response.body()?.size
                                    adapter.submitList(userItem)
                                } else {
                                    Log.e("MainActivity", "onFailure: ${response.message()}")
                                }
                            }

                            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                                Log.e("MainActivity", "onFailure: ${t.message}")
                            }

                        })
                }
            }
        }
        binding.rvGithubUser.adapter = adapter
    }
}
