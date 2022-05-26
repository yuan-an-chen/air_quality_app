package com.example.air_quality_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.air_quality_app.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mainRecords: Records = Records(APIResponse(listOf()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hRecyclerView: RecyclerView = binding.horizontalRecycleView
        val hLayoutManager = LinearLayoutManager(this)
        hLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        hRecyclerView.layoutManager = hLayoutManager

        val vRecyclerView: RecyclerView = binding.verticalRecycleView
        vRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()

    }


    fun fetchData(){
        val targetUrl = "https://data.epa.gov.tw/api/v1/aqx_p_432?limit=1000&api_key=9be7b239-557b-4c10-9775-78cadfc555e9&sort=ImportDate%20desc&format=json"
//        val targetUrl = "https://32cf988a-bd84-48a9-987e-9d3288154b0d.mock.pstmn.io/air_api"

        val request = Request.Builder().url(targetUrl).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("failed to fetch data with error: $e")
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                println("onResponse......")
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                mainRecords.apiResponse = gson.fromJson(body, APIResponse::class.java)

                mainRecords.filterRecords()

                runOnUiThread{
                    binding.horizontalRecycleView.adapter = HorizontalRecyclerAdapter(mainRecords.horizontalRecords)
                    binding.verticalRecycleView.adapter = VerticalRecyclerAdapter(mainRecords.verticalRecords)
                }

            }

        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.toobar_menu, menu)

        val menuItem: MenuItem = menu!!.findItem(R.id.search_item)

        menuItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                binding.horizontalRecycleView.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                mainRecords.filterRecords()
                binding.verticalRecycleView.adapter!!.notifyDataSetChanged()
                binding.horizontalRecycleView.visibility = View.VISIBLE
                return true
            }


        })

        val searchView: androidx.appcompat.widget.SearchView = menuItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isEmpty()){

                }
                else{
                    mainRecords.filterRecords(newText)
                    binding.verticalRecycleView.adapter!!.notifyDataSetChanged()
                }

                return true
            }
        })


        return super.onCreateOptionsMenu(menu)
    }
}

