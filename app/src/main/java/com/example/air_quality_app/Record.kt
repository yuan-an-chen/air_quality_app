package com.example.air_quality_app

import com.google.gson.annotations.SerializedName

data class Record(

    val idx: Int,

    @SerializedName("PM2.5")
    val reading: Int,

    @SerializedName("County")
    val county: String,

    @SerializedName("SiteName")
    val siteName: String,

    @SerializedName("Status")
    val status: String
)

class APIResponse(var records: List<Record>){}


