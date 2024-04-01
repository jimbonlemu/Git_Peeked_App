package com.jimbonlemu.fundamental_android.data.remote.api

import com.jimbonlemu.fundamental_android.data.remote.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.data.remote.response.SearchResponse
import com.jimbonlemu.fundamental_android.data.remote.response.UserItem
import com.jimbonlemu.fundamental_android.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiService {
    @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    fun searchGithubUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    fun getDetailGithubUser(
        @Path("username")username:String
    ):Call<DetailSearchResponse>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    fun getFollowers(@Path("username") username: String): Call<List<UserItem>>

    @GET("/users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.AUTH_TOKEN}")
    fun getFollowing(@Path("username") username: String): Call<List<UserItem>>

}
