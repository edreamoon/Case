package com.ccino.demo.jetpack.paging

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val REPO_LIST = "search/repositories?sort=stars&q=Android"


        val githubApi: GithubService by lazy {
            val retrofit = retrofit2.Retrofit.Builder()
                .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    ).asLenient()
                )
                .build()
            retrofit.create(GithubService::class.java)
        }
    }

    @GET(REPO_LIST)
    suspend fun getRepositories(@Query("page") page: Int, @Query("per_page") perPage: Int): RspRepository

}
