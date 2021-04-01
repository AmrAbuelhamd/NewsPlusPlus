package com.blogspot.soyamr.newsplusplus.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blogspot.soyamr.newsplusplus.domain.model.Article as domainArticle

@Entity
data class Article(
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    fun toDomain() = domainArticle(description,publishedAt,title,url,urlToImage)
}