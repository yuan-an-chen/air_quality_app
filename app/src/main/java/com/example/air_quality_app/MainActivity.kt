package com.example.air_quality_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hRecyclerView: RecyclerView = findViewById(R.id.horizontal_recycle_view)

        val hLayoutManager = LinearLayoutManager(this)
        hLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        hRecyclerView.layoutManager = hLayoutManager

        fetchData()

    }


    fun fetchData(){
        val targetUrl = "https://data.epa.gov.tw/api/v1/aqx_p_432?limit=1000&api_key=9be7b239-557b-4c10-9775-78cadfc555e9&sort=ImportDate%20desc&format=json"
        val request = Request.Builder().url(targetUrl).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("failed to fetch data with error: $e")
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val dataList = gson.fromJson(body, APIResponse::class.java)

            }

        })

    }



//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//
//        menuInflater.inflate(R.menu.menu, menu)
//
//        val menuItem: MenuItem? = menu?.findItem(R.id.item_search)
//
//        val searchView: SearchView = menuItem.actionView
//
//        return super.onCreateOptionsMenu(menu)
//    }
}

