package com.Korinver.androidsivar.retrofit

import com.Korinver.androidsivar.models.Results
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    //Metodo get para hacer llamadas a la API
    @GET("everything")
    fun getResults(@Query("q") q:String, @Query("sortBy") sortBy:String, @Query("language") language:String, @Query("page") page:Int, @Query("apiKey") apiKey:String) : Call<Results>

    companion object {

        var BASE_URL = "https://newsapi.org/v2/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}