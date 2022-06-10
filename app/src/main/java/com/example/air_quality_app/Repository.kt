package com.example.air_quality_app

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request


class Repository{
    fun getClient(): Call{
        val targetUrl = "https://data.epa.gov.tw/api/v1/aqx_p_432?limit=1000&api_key=9be7b239-557b-4c10-9775-78cadfc555e9&sort=ImportDate%20desc&format=json"
//        val targetUrl = "https://data.epa.gov.tw/api/v2/aqx_p_432?api_key=9f0ec647-3bda-41fb-806d-7a4be103053a&sort=ImportDate%20desc&format=json"
//        val targetUrl = "https://32cf988a-bd84-48a9-987e-9d3288154b0d.mock.pstmn.io/air_api"

        val request = Request.Builder().url(targetUrl).build()
        val client = OkHttpClient()

        return client.newCall(request)
    }
}