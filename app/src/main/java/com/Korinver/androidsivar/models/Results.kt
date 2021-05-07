package com.Korinver.androidsivar.models

import com.google.gson.annotations.SerializedName

data class Results(
                    @SerializedName("status")
                    var status: String,
                    @SerializedName ("totalResults")
                   var totalResults:Int,
                    @SerializedName ("articles")
                   var articles: List<Articles>)