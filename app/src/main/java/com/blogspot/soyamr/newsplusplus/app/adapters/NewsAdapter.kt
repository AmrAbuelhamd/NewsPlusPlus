package com.blogspot.soyamr.newsplusplus.app.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.blogspot.soyamr.newsplusplus.domain.model.Article

/**
 * Adapter for the list of articles.
 */
class NewsAdapter(private val onClick: (String) -> Unit) : PagingDataAdapter<Article, NewsViewHolder>(UIMODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent,onClick)
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        article.let {
            holder.bind(article)
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}