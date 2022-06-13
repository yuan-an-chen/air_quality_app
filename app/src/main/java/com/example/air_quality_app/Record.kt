package com.example.air_quality_app

import com.google.gson.annotations.SerializedName

data class Record(

    @SerializedName("siteid")
    val siteId: String,

    @SerializedName("pm2.5")
    var reading: String,

    @SerializedName("county")
    val county: String,

    @SerializedName("sitename")
    val siteName: String,

    @SerializedName("status")
    var status: String
)

data class APIResponse(var records: List<Record>){}


