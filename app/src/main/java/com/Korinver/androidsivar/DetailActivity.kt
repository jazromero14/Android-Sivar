package com.Korinver.androidsivar

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {

    private lateinit var authorData: TextView
    private lateinit var dateData: TextView
    private lateinit var descriptionData : TextView
    private lateinit var contentdata : TextView
    private lateinit var titleData: TextView
    private lateinit var  imageData : ImageView
    private lateinit var botonUrl : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            //What to do on back clicked
            onBackPressed()
        })

        val author: String = intent.getStringExtra("autor")!!
        val date: String = intent.getStringExtra("fecha")!!
        val description : String = intent.getStringExtra("descripcion")!!
        val content : String = intent.getStringExtra("content")!!
        val title: String = intent.getStringExtra("title")!!
        val urlImage = intent.getStringExtra("urlImage")
        val url = intent.getStringExtra("url")

        authorData= findViewById(R.id.authorData)
        dateData = findViewById(R.id.dateData)
        descriptionData = findViewById(R.id.descriptioData)
        contentdata = findViewById(R.id.contentData)
        titleData = findViewById(R.id.titleDetail)
        imageData = findViewById(R.id.imgDataDetail)
        botonUrl = findViewById(R.id.btnUrl)

        authorData.text = author
        dateData.text = date
        descriptionData.text = description
        contentdata.text = content
        titleData.text = title

        Glide.with(this)
            .load(urlImage)
            .placeholder(R.drawable.carga)
            .thumbnail(0.33f)
            .centerCrop()
            .into(imageData)

        openURL(url)

    }

    private fun openURL(url: String?) {

        botonUrl.setOnClickListener {
           val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }
    }
}