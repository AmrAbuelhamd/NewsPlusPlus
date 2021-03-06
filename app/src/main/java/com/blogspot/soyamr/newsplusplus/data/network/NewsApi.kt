package com.blogspot.soyamr.newsplusplus.data.network

import com.blogspot.soyamr.newsplusplus.data.network.model.JsonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything?q=ios&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907")
    suspend fun getNews(@Query("page") page: Int): JsonResponse
}