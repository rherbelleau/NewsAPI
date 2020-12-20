package com.example.newsapi


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.json.JSONArray

class SplashScreenActivity : AppCompatActivity(),ArticleAdapter.OnArticleListener {

    val SourcesLink = "https://newsapi.org/v2/sources?apiKey=99cb64e50a1949e8bc0cbc3ed2011542&language=fr"
    val ArticlesLink = "https://newsapi.org/v2/everything?apiKey=99cb64e50a1949e8bc0cbc3ed2011542&language=fr"

    lateinit var queue: RequestQueue
    var sources = JSONArray()
    var currentSource = ""
    var currentPage = 1
    var articles = mutableListOf<ArticleShape>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        ArticleRecyclerView.layoutManager = LinearLayoutManager(this)
        queue = Volley.newRequestQueue(this)
        dlSources()



    }

    private fun popup (message : String, function : Unit ) {
        val mypopup = AlertDialog.Builder(this)
        mypopup.setMessage(message)
        mypopup.setPositiveButton("Réessayer") { dialog, which ->
            function
        }
        mypopup.setNegativeButton(android.R.string.no, null)
        mypopup.show()
    }

    private fun dlSources() {

        val sourcesRequest = object: JsonObjectRequest(
            Method.GET, SourcesLink, null,
            { response ->
                sources = response.getJSONArray("sources")
                if (sources.length() == 0) {
                    popup("Sources introuvables",dlSources())
                } else {
                    articles = mutableListOf<ArticleShape>()
                    currentSource=sources.getJSONObject(0).getString("id")
                    dlArticles(currentSource,currentPage)

                }
            },
            { error ->
                popup("Echec de la connection : impossible de trouver les sources",dlSources())
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(sourcesRequest)
    }

    private fun dlArticles(source: String, page: Int) {
        progressBar.isVisible = true
        val url = "$ArticlesLink&sources=$source&page=$page"
        val articlesRequest = object: JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                if (response.getJSONArray("articles").length() == 0) {
                    popup("Articles de $source introuvables",dlArticles(source,page))
                } else {
                    val JSONarticles=response.getJSONArray("articles")
                    for (index in 0 until JSONarticles.length()) {
                        val article = JSONarticles.getJSONObject(index)
                        val articleShaped = ArticleShape(article)
                        articles.add(articleShaped)
                    }
                    parent_layout.setBackgroundColor(Color.argb(255, 255, 255, 255))
                    logoView.isGone =true
                    progressBar.isVisible = false
                    ArticleRecyclerView.adapter = ArticleAdapter(articles,this)

                }
            },
            { error ->
                popup("Echec de la connection : impossible de trouver les articles de $source",dlArticles(source,page))
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(articlesRequest)
    }

    override fun onArticleClick(position: Int) {
        val article = articles[position]
        val Intent = Intent(this, LayoutArticle::class.java)
        Intent.putExtra("date", article.publishedAt)
        Intent.putExtra("sourceName", article.sourceName)
        Intent.putExtra("title", article.title)
        Intent.putExtra("author", article.author)
        Intent.putExtra("description", article.description)
        Intent.putExtra("url", article.url)
        Intent.putExtra("urlToImage", article.urlToImage)
        startActivity(Intent)
    }


}

