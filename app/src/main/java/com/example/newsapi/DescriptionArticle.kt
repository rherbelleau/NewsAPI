package com.example.newsapi

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_description.*


class DescriptionArticle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_description)

        date_description.text = intent.getStringExtra("date")
        title_description.text = intent.getStringExtra("title")
        author_description.text = intent.getStringExtra("author")
        source_description.text = intent.getStringExtra("sourceName")
        description_description.text = intent.getStringExtra("description")
        Picasso.get().load(intent.getStringExtra("urlToImage")).into(article_image)

    }

}

