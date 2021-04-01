package com.blogspot.soyamr.newsplusplus.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blogspot.soyamr.newsplusplus.data.db.model.Article


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>): List<Long>

    @Query("SELECT * FROM article")
    fun getAll(): PagingSource<Int, Article>

    @Query("SELECT * FROM article WHERE id like :id")
    fun findArticleById(id: String): Article

    @Query("DELETE FROM article")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM article")
    fun getCount(): Int

}