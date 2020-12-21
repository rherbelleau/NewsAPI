package com.example.newsapi


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.json.JSONArray

class SplashScreenActivity : AppCompatActivity(),ArticleAdapter.OnArticleListener, ArticleAdapter.OnBottomListener {

    val key= "99cb64e50a1949e8bc0cbc3ed2011542"

    val SourcesLink = "https://newsapi.org/v2/sources?apiKey=6149da01c90e4b5b80c78e2dccaef212&language=fr"
    val ArticlesLink = "https://newsapi.org/v2/everything?apiKey=6149da01c90e4b5b80c78e2dccaef212&language=fr"


    var sources = JSONArray()
    var currentSource = ""
    var currentPage = 1
    var articles = mutableListOf<ArticleShape>()

    lateinit var viewAdapter : ArticleAdapter
    lateinit var queue: RequestQueue
    lateinit var alertDialogBuilderSource: androidx.appcompat.app.AlertDialog.Builder
    lateinit var alertDialogBuilderArticle: androidx.appcompat.app.AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        ArticleRecyclerView.layoutManager = LinearLayoutManager(this)
        queue = Volley.newRequestQueue(this)

        SetUpAlertDialogBuilderSource()
        SetUpAlertDialogBuilderArticle()
        dlSources()

    }

    private fun SetUpAlertDialogBuilderSource() {
        alertDialogBuilderSource = this.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
        }
        alertDialogBuilderSource.setTitle("Une erreur est survenue").apply {
            setPositiveButton("Réessayer") { _, _ ->
                dlSources()
                setNegativeButton("Annuler") { _, _ ->
                    progressBar.isVisible = false
                }
            }
        }
    }
    private fun showAlertDialogSource(message: String) {
        alertDialogBuilderSource.setMessage(message)
        val alertDialog: androidx.appcompat.app.AlertDialog = alertDialogBuilderSource.create()
        alertDialog.show()
    }

    private fun dlSources() {
        val sourcesRequest = object: JsonObjectRequest(
            Method.GET, SourcesLink, null,
            { response ->
                sources = response.getJSONArray("sources")
                if (sources.length() == 0) {
                    showAlertDialogSource("Pas de source disponible")
                } else {
                    articles = mutableListOf<ArticleShape>()
                    currentSource=sources.getJSONObject(1).getString("id")
                    dlArticles(currentSource,currentPage)
                }
            },
            { error ->
                showAlertDialogSource("Impossible de télécharger les sources")
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


    private fun SetUpAlertDialogBuilderArticle() {
        alertDialogBuilderArticle = this.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
        }
        alertDialogBuilderSource.setTitle("Une erreur est survenue").apply {
            setPositiveButton("Réessayer") { _, _ ->
                dlArticles(currentSource,currentPage)
                setNegativeButton("Annuler") { _, _ ->
                    progressBar.isVisible = false
                }
            }
        }
    }
    private fun showAlertDialogArticle(message: String) {
        alertDialogBuilderArticle.setMessage(message)
        val alertDialog: androidx.appcompat.app.AlertDialog = alertDialogBuilderSource.create()
        alertDialog.show()
    }

    private fun dlArticles(source: String, page: Int) {
        progressBar.isVisible = true
        val url = "$ArticlesLink&sources=$source&page=$page"
        val articlesRequest = object: JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                if (response.getJSONArray("articles").length() == 0) {
                    showAlertDialogArticle("Aucun article disponible")
                } else {
                    if (page < 6) {
                        var premierArticle = 1
                        if (page == 1) {
                            articles = mutableListOf<ArticleShape>()
                            parent_layout.setBackgroundColor(Color.argb(255, 255, 255, 255))
                            logoView.isGone = true
                            premierArticle=0
                        }
                        val JSONarticles = response.getJSONArray("articles")
                        val newArticles = mutableListOf<ArticleShape>()
                        for (index in premierArticle until JSONarticles.length()) {
                            val article = JSONarticles.getJSONObject(index)
                            val articleShaped = ArticleShape(article)
                            articles.add(articleShaped)
                            newArticles.add(articleShaped)
                        }

                        if (page == 1) {
                            viewAdapter=ArticleAdapter(articles, this, this)
                            ArticleRecyclerView.adapter = viewAdapter
                        } else {
                            viewAdapter.addArticles(newArticles)
                        }
                        progressBar.isVisible = false
                    }
                }
            },
            { error ->
                showAlertDialogArticle("Impossible de télécharger les Articles")
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
        val Intent = Intent(this, DescriptionArticle::class.java)
        Intent.putExtra("date", article.publishedAt)
        Intent.putExtra("sourceName", article.sourceName)
        Intent.putExtra("title", article.title)
        Intent.putExtra("author", article.author)
        Intent.putExtra("description", article.description)
        Intent.putExtra("url", article.url)
        Intent.putExtra("urlToImage", article.urlToImage)
        startActivity(Intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_layout, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (sources.length() > 0) {
            menu?.clear()
        }
        for (index in 0 until sources.length()) {
            menu?.add(0, index, index, sources.getJSONObject(index).getString("name"))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        currentSource = sources.getJSONObject(item.itemId).getString("id")
        currentPage = 1
        dlArticles(currentSource, currentPage)
        return true
    }

    override fun onBottomReached(position: Int) {
        currentPage+=1
        dlArticles(currentSource, currentPage)
    }


}

