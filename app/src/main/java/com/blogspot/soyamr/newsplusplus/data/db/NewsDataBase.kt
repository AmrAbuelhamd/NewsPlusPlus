package com.blogspot.soyamr.newsplusplus.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blogspot.soyamr.newsplusplus.data.db.dao.ArticleDao
import com.blogspot.soyamr.newsplusplus.data.db.dao.RemoteKeysDao
import com.blogspot.soyamr.newsplusplus.data.db.model.Article
import com.blogspot.soyamr.newsplusplus.data.db.model.RemoteKeys


@Database(entities = [Article::class, RemoteKeys::class], version = 4)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun keysDao(): RemoteKeysDao
}