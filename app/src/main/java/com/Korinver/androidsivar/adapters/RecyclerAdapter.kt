package com.Korinver.androidsivar.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.Korinver.androidsivar.DetailActivity
import com.Korinver.androidsivar.R
import com.Korinver.androidsivar.models.Articles
import com.bumptech.glide.Glide


class RecyclerAdapter(private val context: Context, private val mArticles: List<Articles>, private val mCardViewLayout: Int) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(mCardViewLayout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        var imgUrl = mArticles[i].urlToImage
         viewHolder.itemTitle.text = mArticles[i].title
        viewHolder.itemDate.text = mArticles[i].publishedAt
        Glide.with(viewHolder.itemImg)
            .load(imgUrl)
            .placeholder(R.drawable.carga)
            .thumbnail(0.33f)
            .centerCrop()
            .into(viewHolder.itemImg)
        viewHolder.cardItems.setOnClickListener {
            val context = context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("autor", mArticles[i].author)
                putExtra("fecha", mArticles[i].publishedAt)
                putExtra("descripcion", mArticles[i].description)
                putExtra("content", mArticles[i].content)
                putExtra("title", mArticles[i].title)
                putExtra("urlImage", mArticles[i].urlToImage)
                putExtra("url", mArticles[i].url)
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mArticles.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardItems : CardView = itemView.findViewById(R.id.card_pertanyaan)
        var itemImg: ImageView = itemView.findViewById(R.id.imgData)
        var itemTitle: TextView = itemView.findViewById(R.id.cardTitle)
        var itemDate: TextView = itemView.findViewById(R.id.date)

    }

}