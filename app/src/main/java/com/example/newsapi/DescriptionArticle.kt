package com.example.newsapi

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
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

        findViewById<Button>(R.id.button_description).setOnClickListener{
            val Intent = Intent(this, WebActivity::class.java)
            Intent.putExtra("url", intent.getStringExtra("url"))
            startActivity(Intent)
        }

    }


}

