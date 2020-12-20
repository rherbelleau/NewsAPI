package com.example.newsapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_layout.view.*

class ArticleAdapter(val items: List<ArticleShape>, private val ListenerOnArticle: OnArticleListener ) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    class ViewHolder(private val view: View,private val oAL: OnArticleListener) : RecyclerView.ViewHolder(view) {
        fun bindArticle(articleView: ArticleShape) {
            with(articleView) {
                view.setOnClickListener{
                    oAL.onArticleClick(adapterPosition)}
                itemView.title_layout.text = title
                itemView.date.text = publishedAt
                if (urlToImage != null) {
                Picasso.get().load(urlToImage).into(itemView.article_image)}
                if (author != "null") {
                    itemView.author.text = author}
            }
        }
    }




    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lineView = LayoutInflater.from(parent.context).inflate(R.layout.article_layout, parent, false)
        return ViewHolder(lineView,ListenerOnArticle)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindArticle(items[position])
    }

    interface OnArticleListener{
        fun onArticleClick(position: Int)
    }


}