package com.blogspot.soyamr.newsplusplus.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.blogspot.soyamr.newsplusplus.data.db.NewsDataBase
import com.blogspot.soyamr.newsplusplus.data.db.model.Article
import com.blogspot.soyamr.newsplusplus.data.db.model.RemoteKeys
import com.blogspot.soyamr.newsplusplus.data.network.NewsApi
import retrofit2.HttpException
import java.io.IOException

private const val NEWS_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsDataBase: NewsDataBase,
    private val service: NewsApi,
) : RemoteMediator<Int, Article>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: NEWS_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If the previous key is null, then the list is empty so we should wait for data
                // fetched by remote refresh and can simply skip loading this time by returning
                // `false` for endOfPaginationReached.
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If the next key is null, then the list is empty so we should wait for data
                // fetched by remote refresh and can simply skip loading this time by returning
                // `false` for endOfPaginationReached.
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                nextKey
            }
        }

        try {
            val apiResponse = service.getNews(page)

            val articles = apiResponse.articles
            val endOfPaginationReached = articles.isEmpty() || page == 5
            newsDataBase.withTransaction {
                with(newsDataBase) {
                    // clear all tables in the database
                    if (loadType == LoadType.REFRESH) {
                        keysDao().clearRemoteKeys()
                        articleDao().deleteAll()
                    }
                    val prevKey = if (page == NEWS_STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    val articlesIds = articleDao().insertAll(articles.map { it.toDb() })
                    val keys = articlesIds.map {
                        RemoteKeys(it, prevKey, nextKey)
                    }
                    keysDao().insertAll(keys)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                newsDataBase.keysDao().remoteKeysArticleId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                // Get the remote keys of the first items retrieved
                newsDataBase.keysDao().remoteKeysArticleId(article.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { articleId ->
                newsDataBase.keysDao().remoteKeysArticleId(articleId)
            }
        }
    }
}
