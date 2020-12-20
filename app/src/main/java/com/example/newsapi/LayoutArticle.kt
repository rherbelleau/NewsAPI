package com.example.newsapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.article_description.*

class LayoutArticle : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_layout)
        /*date_description.text = intent.getStringExtra("date")
        title_description.text = intent.getStringExtra("title")
        author_description.text = intent.getStringExtra("author")
        source_description.text = intent.getStringExtra("sourceName")
        description_description.text = intent.getStringExtra("description")*/

    }
}