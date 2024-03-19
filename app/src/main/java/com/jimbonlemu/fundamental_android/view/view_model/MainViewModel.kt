package com.jimbonlemu.fundamental_android.view.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jimbonlemu.fundamental_android.data.api.ApiConfig
import com.jimbonlemu.fundamental_android.data.response.SearchResponse
import com.jimbonlemu.fundamental_android.data.response.UserItem
import com.jimbonlemu.fundamental_android.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _searchResult = MutableLiveData<List<UserItem>?>()
    val searchResult: LiveData<List<UserItem>?> = _searchResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _spawnSnackBar = MutableLiveData<Event<String>>()
    val spawnSnackBar: LiveData<Event<String>> = _spawnSnackBar


    init {
        searchGithubUser()
    }

    fun searchGithubUser(username: String = "jimbonlemu") {
        _isLoading.value = true
        val client = ApiConfig.connectApiService().searchGithubUser(username)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchResult.value = response.body()?.items
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }


}