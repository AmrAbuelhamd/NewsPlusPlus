package com.blogspot.soyamr.newsplusplus.data

import androidx.paging.*
import com.blogspot.soyamr.newsplusplus.data.db.NewsDataBase
import com.blogspot.soyamr.newsplusplus.data.network.NewsApi
import com.blogspot.soyamr.newsplusplus.domain.Repository
import com.blogspot.soyamr.newsplusplus.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val newsDataBase: NewsDataBase,
    private val api: NewsApi,
) :
    Repository {
    override fun getNews(): Flow<PagingData<Article>> {

        val pagingSourceFactory = { newsDataBase.articleDao().getAll() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            remoteMediator = NewsRemoteMediator(newsDataBase, api),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { it -> it.map { it.toDomain() } }
    }

}
