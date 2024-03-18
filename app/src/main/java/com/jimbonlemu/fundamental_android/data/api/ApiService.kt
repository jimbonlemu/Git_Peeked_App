package com.jimbonlemu.fundamental_android.data.api

import com.jimbonlemu.fundamental_android.data.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.data.response.SearchResponse
import com.jimbonlemu.fundamental_android.data.response.UserItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    fun searchGithubUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("/users/{username}")
    fun getDetailGithubUser(
        @Path("username")username:String
    ):Call<DetailSearchResponse>

    @GET
    fun getFollowers(
        @Url url: String
    ): Call<List<UserItem>>

    @GET("/users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserItem>>

}