package com.jimbonlemu.fundamental_android.view.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jimbonlemu.fundamental_android.data.api.ApiConfig
import com.jimbonlemu.fundamental_android.data.response.UserItem
import com.jimbonlemu.fundamental_android.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val _followingData = MutableLiveData<List<UserItem>?>()
    val followingData: LiveData<List<UserItem>?> = _followingData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _spawnSnackBar = MutableLiveData<Event<String>>()
    val spawnSnackBar: LiveData<Event<String>> = _spawnSnackBar

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getFollowingGithubUser(username: String) {
        _isError.value = ""
        _isLoading.value = true
        val client = ApiConfig.connectApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UserItem>?> {
            override fun onResponse(
                call: Call<List<UserItem>?>,
                response: Response<List<UserItem>?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followingData.value = response.body()
                    _isError.value =
                        if (_followingData.value!!.isEmpty() || _followingData.value!!.equals(0)) "This user has no Following" else ""

                } else {
                    Log.e("Followers View Model", "onFailure: ${response.message()}")
                    _spawnSnackBar.value = Event(response.message())
                    _isError.value = "Unable to fetch Following data"
                }
            }

            override fun onFailure(call: Call<List<UserItem>?>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message}")
                _spawnSnackBar.value = Event(t.message.toString())
            }

        })
    }
}
