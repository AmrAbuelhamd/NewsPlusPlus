package com.blogspot.soyamr.newsplusplus.data.network.model


data class JsonResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)