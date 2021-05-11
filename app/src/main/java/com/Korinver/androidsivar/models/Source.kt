package com.Korinver.androidsivar.models

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

//Modelo de segundo nivel en la estructura de la respuesta JSON de la API
data class Source(@Nullable
                  @SerializedName ("id")
                  var id: String,
                  @SerializedName ("name")
                  var name: String)
