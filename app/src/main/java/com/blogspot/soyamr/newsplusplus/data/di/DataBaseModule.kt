package com.blogspot.soyamr.newsplusplus.data.di

import android.content.Context
import androidx.room.Room
import com.blogspot.soyamr.newsplusplus.data.db.NewsDataBase
import com.blogspot.soyamr.newsplusplus.data.db.dao.ArticleDao
import com.blogspot.soyamr.newsplusplus.data.db.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun getDataBaseObject(@ApplicationContext context: Context): NewsDataBase =
        Room.databaseBuilder(
            context,
            NewsDataBase::class.java, "news-database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun getArticleDao(db: NewsDataBase): ArticleDao = db.articleDao()

    @Provides
    @Singleton
    fun getKeysDao(db: NewsDataBase): RemoteKeysDao = db.keysDao()

}