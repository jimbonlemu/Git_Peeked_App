package com.jimbonlemu.fundamental_android.data.remote.api

import com.jimbonlemu.git_peeked.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun connectApiService(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

            val chainInterceptorForAuthToken = Interceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .build()
                )
            }

            val clientAndInterceptorBuilder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chainInterceptorForAuthToken)
                .build()

            val createAndBuildConnectionToClient =
                Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientAndInterceptorBuilder)
                    .build()
            return createAndBuildConnectionToClient.create(ApiService::class.java)
        }
    }
}
