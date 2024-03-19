package com.jimbonlemu.fundamental_android.data.api

import com.jimbonlemu.fundamental_android.data.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.data.response.SearchResponse
import com.jimbonlemu.fundamental_android.data.response.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface ApiService {
    @GET("/search/users")
    fun searchGithubUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("/users/{username}")
    fun getDetailGithubUser(
        @Path("username")username:String
    ):Call<DetailSearchResponse>

    @GET("/users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserItem>>

    @GET("/users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserItem>>

}