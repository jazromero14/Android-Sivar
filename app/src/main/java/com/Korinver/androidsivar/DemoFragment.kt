package com.Korinver.androidsivar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Korinver.androidsivar.adapters.RecyclerAdapter
import com.Korinver.androidsivar.models.Articles
import com.Korinver.androidsivar.models.Results
import com.Korinver.androidsivar.retrofit.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DemoFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
   private  var mArticles : MutableList<Articles> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_demo, container, false)
        layoutManager = LinearLayoutManager(activity)
        // set the custom adapter to the RecyclerView
        adapter = RecyclerAdapter(requireContext() , mArticles, R.layout.cardview)
        return rootView
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val API_KEY = "03715fe40d8a47cdb59a8860330e57e5"
        val apiInterface = ApiInterface.create().getResults("android", API_KEY)

        //recycler
        val recyclerView: RecyclerView = view!!.findViewById(R.id.recycler_view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        // define an adapter
        adapter = RecyclerAdapter(requireContext(), mArticles, R.layout.cardview)
        recyclerView.adapter = adapter

        apiInterface.enqueue(object : Callback<Results> {
            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.e("results", t.toString())
            }

            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                if (response.body() != null){
                    val results = response.body().toString()
                    val articles = response.body()!!.articles
                    mArticles.addAll(articles)
                    adapter!!.notifyDataSetChanged()
                    Log.e("results", articles.toString())

                }
            }
        })
    }
}
