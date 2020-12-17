package com.example.newsapi

import org.json.JSONObject

class ArticleShape {
    var sourceName: String = ""
    var author: String = ""
    var title: String = ""
    var description: String = ""
    var url: String = ""
    var urlToImage: String? = ""
    var publishedAt: String = ""



    constructor(article: JSONObject) {
        this.sourceName = article.getJSONObject("source").getString("name")
        this.author = article.getString("author")
        this.title = article.getString("title")
        this.description = article.getString("description")
        this.url = article.getString("url")
        this.urlToImage = article.getString("urlToImage")
        this.publishedAt = article.getString("publishedAt").subSequence(0,10).toString()

    }

}