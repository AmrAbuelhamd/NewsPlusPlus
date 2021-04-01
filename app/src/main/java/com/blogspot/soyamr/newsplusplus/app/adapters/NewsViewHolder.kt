package com.blogspot.soyamr.newsplusplus.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.soyamr.newsplusplus.databinding.ArticleViewItemBinding
import com.blogspot.soyamr.newsplusplus.domain.model.Article

/**
 * View Holder for a [Article] RecyclerView list item.
 */
class NewsViewHolder(val binding: ArticleViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private var article: Article? = null

    init {
//        view.setOnClickListener {
//            article?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
//        }
    }

    fun bind(article: Article?) {
        article?.let {
            with(binding) {
                titleTextView.text = it.title
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ArticleViewItemBinding
                .inflate(layoutInflater, parent, false)
            return NewsViewHolder(binding)
        }
    }
}
