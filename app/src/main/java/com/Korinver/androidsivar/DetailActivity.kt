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

    //Declaracion de variables goblales
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
        //se declara la toolbar personalizada
        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            //se establece la accion
            onBackPressed()
        })

        //Se recuperan los datos enviados desde DemoFragment.kt
        val author: String = intent.getStringExtra("autor")!!
        val date: String = intent.getStringExtra("fecha")!!
        val description : String = intent.getStringExtra("descripcion")!!
        val content : String = intent.getStringExtra("content")!!
        val title: String = intent.getStringExtra("title")!!
        val urlImage = intent.getStringExtra("urlImage")
        val url = intent.getStringExtra("url")

        //se inicializan las variables con los elementos de la vista
        authorData= findViewById(R.id.authorData)
        dateData = findViewById(R.id.dateData)
        descriptionData = findViewById(R.id.descriptioData)
        contentdata = findViewById(R.id.contentData)
        titleData = findViewById(R.id.titleDetail)
        imageData = findViewById(R.id.imgDataDetail)
        botonUrl = findViewById(R.id.btnUrl)

        //se establecen los datos obtenidos desde DemoFragment hacia las variables con los elementos de la vista
        authorData.text = author
        dateData.text = date
        descriptionData.text = description
        contentdata.text = content
        titleData.text = title

        //Con la herramienta de GLIDE se carga la URL de la imagen para poder mostarla en un formato drawable para un ImageView
        Glide.with(this)
            .load(urlImage)
            .placeholder(R.drawable.carga)
            .thumbnail(0.33f)
            .centerCrop()
            .into(imageData)

        openURL(url)

    }

    //Función que abre la URL del link de la información
    private fun openURL(url: String?) {

        botonUrl.setOnClickListener {
           val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }
    }
}