package com.example.newsapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.article_layout.view.*

class ArticleAdapter(val items: List<ArticleShape>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindArticle(articleView: ArticleShape) {
            with(articleView) {
                itemView.title_layout.text = title
                itemView.date.text = publishedAt
                itemView.author.text = author
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lineView = LayoutInflater.from(parent.context).inflate(R.layout.article_layout, parent, false)
        return ViewHolder(lineView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindArticle(items[position])
    }


}