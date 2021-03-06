package com.blogspot.soyamr.newsplusplus.presentation.news_list.news.adapters.news_load_state_adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class NewsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<NewsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: NewsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NewsLoadStateViewHolder {
        return NewsLoadStateViewHolder.create(parent, retry)
    }
}
