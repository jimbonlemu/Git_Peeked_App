package com.jimbonlemu.fundamental_android.view.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jimbonlemu.fundamental_android.data.api.ApiConfig
import com.jimbonlemu.fundamental_android.data.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _userDataDetail = MutableLiveData<DetailSearchResponse>()
    val userDataDetail:LiveData<DetailSearchResponse> = _userDataDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _spawnSnackBar = MutableLiveData<Event<String>>()
    val spawnSnackBar:LiveData<Event<String>> = _spawnSnackBar


    fun getDataDetailUserGithub(username:String? = null){
        _isLoading.value = true
        val client = ApiConfig.connectApiService().getDetailGithubUser(username!!)
        client.enqueue(object :Callback<DetailSearchResponse>{
            override fun onResponse(
                call: Call<DetailSearchResponse>,
                response: Response<DetailSearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _userDataDetail.value = response.body()
                }else{
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailSearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message}")
            }

        })
    }
}