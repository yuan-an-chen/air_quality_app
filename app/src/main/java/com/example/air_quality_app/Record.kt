package com.example.air_quality_app

data class Record(
    val idx: Int,
    val reading: Int,
    val county: String,
    val siteName: String,
    val status: String
)


