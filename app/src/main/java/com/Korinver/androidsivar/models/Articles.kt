package com.Korinver.androidsivar.models

import com.google.gson.annotations.SerializedName

/* Modelo con el que se reciben los datos de la API, con su correspondiente estructura
*/
data class Articles(@SerializedName("source")
                    var source: Source,
                    @SerializedName ("author")
                    var author: String,
                    @SerializedName ("title")
                    var title: String,
                    @SerializedName ("description")
                    var description: String,
                    @SerializedName ("url")
                    var url:String,
                    @SerializedName ("urlToImage")
                    var urlToImage: String,
                    @SerializedName ("publishedAt")
                    var publishedAt:String,
                    @SerializedName ("content")
                    var content:String )
