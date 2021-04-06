package com.blogspot.soyamr.newsplusplus.presentation.news_list.news.adapters.news_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.soyamr.newsplusplus.R
import com.blogspot.soyamr.newsplusplus.databinding.ArticleViewItemBinding
import com.blogspot.soyamr.newsplusplus.domain.model.Article
import com.squareup.picasso.Picasso

/**
 * View Holder for a [Article] RecyclerView list item.
 */
class NewsViewHolder private constructor(private val binding: ArticleViewItemBinding, private val onClick: (String) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(article: Article?) {
        article?.let {
            with(binding) {
                titleTextView.text = it.title
                descriptionTextView.text =
                    HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
                Picasso.get().load(it.urlToImage).placeholder(R.drawable.no_image2).into(imageView)
                dateTextView.text = it.publishedAt
                val webUrl = it.url
                root.setOnClickListener { onClick(webUrl) }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (String) -> Unit): NewsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ArticleViewItemBinding
                .inflate(layoutInflater, parent, false)
            return NewsViewHolder(binding, onClick)
        }
    }
}
