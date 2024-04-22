package com.dicoding.asclepius.data.remote

import com.dicoding.asclepius.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/everything")
    fun getArticle(
        @Query("q") query: String = "skin cancer",
        @Query("apiKey") token: String = BuildConfig.API_TOKEN,
        @Query("language") lang: String = "en"
    ): Call<ArticlesResponse>

}