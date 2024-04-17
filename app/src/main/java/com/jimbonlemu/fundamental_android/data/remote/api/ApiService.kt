package com.jimbonlemu.fundamental_android.data.remote.api

import com.jimbonlemu.fundamental_android.data.remote.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.data.remote.response.SearchResponse
import com.jimbonlemu.fundamental_android.data.remote.response.UserItem
import com.jimbonlemu.git_peeked.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val API_HEADER = "Authorization: token ${BuildConfig.AUTH_TOKEN}"
    }

    @GET("/search/users")
    @Headers(API_HEADER)
    fun searchGithubUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("/users/{username}")
    @Headers(API_HEADER)
    fun getDetailGithubUser(
        @Path("username") username: String
    ): Call<DetailSearchResponse>

    @GET("/users/{username}/followers")
    @Headers(API_HEADER)
    fun getFollowers(@Path("username") username: String): Call<List<UserItem>>

    @GET("/users/{username}/following")
    @Headers(API_HEADER)
    fun getFollowing(@Path("username") username: String): Call<List<UserItem>>

}
