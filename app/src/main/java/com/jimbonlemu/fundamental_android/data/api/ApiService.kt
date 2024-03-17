package com.jimbonlemu.fundamental_android.data.api

import com.jimbonlemu.fundamental_android.data.models.SearchResponse
import com.jimbonlemu.fundamental_android.data.models.UserItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    fun searchGithubUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET
    fun getFollowers(
        @Url url: String
    ): Call<List<UserItem>>

}