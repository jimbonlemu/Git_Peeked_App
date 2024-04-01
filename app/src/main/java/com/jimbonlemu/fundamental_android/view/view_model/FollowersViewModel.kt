package com.jimbonlemu.fundamental_android.view.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jimbonlemu.fundamental_android.data.remote.api.ApiConfig
import com.jimbonlemu.fundamental_android.data.remote.response.UserItem
import com.jimbonlemu.fundamental_android.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val _followersData = MutableLiveData<List<UserItem>?>()
    val followersData: LiveData<List<UserItem>?> = _followersData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getFollowersGithubUser(username: String) {
        _isError.value = ""
        _isLoading.value = true
        val client = ApiConfig.connectApiService().getFollowers(username)
        client.enqueue(object : Callback<List<UserItem>?> {
            override fun onResponse(
                call: Call<List<UserItem>?>,
                response: Response<List<UserItem>?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followersData.value = response.body()
                    _isError.value =
                        if (_followersData.value!!.isEmpty() || _followersData.value!!.equals(0)) "This user has no Followers" else ""
                } else {
                    Log.e("Followers View Model", "onFailure: ${response.message()}")
                    _isError.value = "Unable to fetch Followers data"
                }
            }

            override fun onFailure(call: Call<List<UserItem>?>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message}")
            }

        })
    }
}
