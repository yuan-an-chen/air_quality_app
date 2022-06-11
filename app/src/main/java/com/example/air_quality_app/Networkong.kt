package com.example.air_quality_app

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitInstance{
//    private const val targetUrl = "https://data.epa.gov.tw/api/v1/"
//    private const val targetUrl = "https://data.epa.gov.tw/api/v1/aqx_p_432?limit=1000&api_key=9be7b239-557b-4c10-9775-78cadfc555e9&sort=ImportDate%20desc&format=json/"
//        val targetUrl = "https://data.epa.gov.tw/api/v2/aqx_p_432?api_key=9f0ec647-3bda-41fb-806d-7a4be103053a&sort=ImportDate%20desc&format=json"
    const val targetUrl = "https://32cf988a-bd84-48a9-987e-9d3288154b0d.mock.pstmn.io/"
    val instance = Retrofit.Builder()
        .baseUrl(targetUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface APIService{

    @GET("air_api")
//    @GET("aqx_p_432?limit=1000&api_key=9be7b239-557b-4c10-9775-78cadfc555e9&sort=ImportDate%20desc&format=json")
    suspend fun fetchData(): APIResponse
}