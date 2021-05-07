package com.Korinver.androidsivar.models

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName


data class Source(@Nullable
                  @SerializedName ("id")
                  var id: String,
                  @SerializedName ("name")
                  var name: String)
