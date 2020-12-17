package com.example.newsapi


import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.json.JSONArray

class SplashScreenActivity : AppCompatActivity() {


    val SOURCES_URL = "https://newsapi.org/v2/sources?apiKey=99cb64e50a1949e8bc0cbc3ed2011542&language=fr"
    val BASE_ARTICLES_URL = "https://newsapi.org/v2/everything?apiKey=99cb64e50a1949e8bc0cbc3ed2011542&language=fr"

    lateinit var queue: RequestQueue
    var sources = JSONArray()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        queue = Volley.newRequestQueue(this)
        uploadsource()


    }


    private fun uploadsource () {
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, SOURCES_URL, null,
            { response ->
                sources = response
                if (sources.length() == 0) {
                    val mypopup = AlertDialog.Builder(this)
                    mypopup.setMessage("Pas de source disponible")
                    mypopup.setPositiveButton("Réessayer") { dialog, which ->
                        uploadsource()
                    }
                    mypopup.setNegativeButton(android.R.string.no, null)
                    mypopup.show()
                } else {
                    val Intent = Intent(this, MainActivity::class.java)
                    startActivity(Intent)
                }
            },
            { error ->
                val mypopup = AlertDialog.Builder(this)
                mypopup.setMessage("Impossible de récupérer les données sources")
                mypopup.setPositiveButton("Réessayer") { dialog, which ->
                    uploadsource()
                }
                mypopup.setNegativeButton(android.R.string.no, null)
                mypopup.show()
            }
        )
        queue.add(jsonObjectRequest)
    }

}

