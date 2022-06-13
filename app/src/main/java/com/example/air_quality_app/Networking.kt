package com.example.air_quality_app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitInstance{
    private const val targetUrl = "https://data.epa.gov.tw/api/v2/"
//    const val targetUrl = "https://3229ae49-44bd-4e1c-bdc2-41424f0207ca.mock.pstmn.io/"
    val instance = Retrofit.Builder()
        .baseUrl(targetUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface APIService{

//    @GET("air_api")
    @GET("aqx_p_432?limit=1000&api_key=cebebe84-e17d-4022-a28f-81097fda5896&sort=ImportDate%20desc&format=json")
    suspend fun fetchData(): APIResponse
}