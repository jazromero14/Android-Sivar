package com.Korinver.androidsivar.models

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("status")
    var status: String,
    @SerializedName("code")
    var code:String,
    @SerializedName("message")
    var message: String)