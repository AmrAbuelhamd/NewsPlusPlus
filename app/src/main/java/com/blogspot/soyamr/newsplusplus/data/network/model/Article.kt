package com.blogspot.soyamr.newsplusplus.data.network.model

import com.blogspot.soyamr.newsplusplus.data.db.model.Article as dbArticle

data class Article(
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String?
) {
    fun toDb() = dbArticle(description, publishedAt, title, url, urlToImage)
}