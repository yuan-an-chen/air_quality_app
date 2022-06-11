package com.example.air_quality_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.air_quality_app.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

//    private var mainRecords: Records = Records(APIResponse(listOf()))

    private val mainViewModel by viewModels<RecordViewModel>{
        RecordViewModelFactory(Repository.getInstance())
    }

    val handler = Handler(Looper.getMainLooper())

//    private val fetchDataRunnable: Runnable = object : Runnable{
//        override fun run() {
//            println("fetching data...")
//            fetchData()
//            handler.postDelayed(this, 600000)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchViewInfoLayout.visibility = View.GONE

        title = getString(R.string.app_title)

        val hRecyclerView: RecyclerView = binding.horizontalRecycleView
        val hLayoutManager = LinearLayoutManager(this)
        hLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        hRecyclerView.layoutManager = hLayoutManager
        val horizontalRecyclerAdapter = HorizontalRecyclerAdapter()

        val vRecyclerView: RecyclerView = binding.verticalRecycleView
        vRecyclerView.layoutManager = LinearLayoutManager(this)
        val verticalRecyclerAdapter = VerticalRecyclerAdapter()

        binding.horizontalRecycleView.adapter = horizontalRecyclerAdapter
        binding.verticalRecycleView.adapter = verticalRecyclerAdapter

        mainViewModel.horizontalListLiveData.observe(this) {
            horizontalRecyclerAdapter.submitList(it)
            horizontalRecyclerAdapter.notifyDataSetChanged()
        }

        mainViewModel.verticalListLiveData.observe(this) {
            verticalRecyclerAdapter.submitList(it)
            verticalRecyclerAdapter.notifyDataSetChanged()
        }


//        binding.verticalRefresh.setOnRefreshListener {
//            fetchData()
//        }

//        handler.post(fetchDataRunnable)
    }



//    fun fetchData(){
//        val targetUrl = "https://data.epa.gov.tw/api/v1/aqx_p_432?limit=1000&api_key=9be7b239-557b-4c10-9775-78cadfc555e9&sort=ImportDate%20desc&format=json"
////        val targetUrl = "https://data.epa.gov.tw/api/v2/aqx_p_432?api_key=9f0ec647-3bda-41fb-806d-7a4be103053a&sort=ImportDate%20desc&format=json"
////        val targetUrl = "https://32cf988a-bd84-48a9-987e-9d3288154b0d.mock.pstmn.io/air_api"
//
//        val request = Request.Builder().url(targetUrl).build()
//        val client = OkHttpClient()
//        client.newCall(request).enqueue(object: Callback{
//            override fun onFailure(call: Call, e: IOException) {
//                println("failed to fetch data with error: $e")
//            }
//
//            override fun onResponse(call: Call, response: okhttp3.Response) {
//                println("onResponse......")
//                val body = response.body?.string()
//                val gson = GsonBuilder().create()
//                mainRecords.apiResponse = gson.fromJson(body, APIResponse::class.java)
//
//                mainRecords.filterRecords()
//
//                runOnUiThread {
//                    binding.horizontalRecycleView.adapter!!.notifyDataSetChanged()
//                    binding.verticalRecycleView.adapter!!.notifyDataSetChanged()
//                    binding.verticalRefresh.isRefreshing = false
//                }
//
//            }
//
//        })
//
//    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.toobar_menu, menu)

        val menuItem: MenuItem = menu!!.findItem(R.id.search_item)

        menuItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                binding.horizontalRecycleView.visibility = View.GONE
                binding.verticalRecycleView.visibility = View.GONE
                binding.searchViewInfoLayout.visibility = View.VISIBLE

                binding.verticalRefresh.isEnabled = false

                binding.searchViewInfoTV.text = getString(R.string.empty_search_view_info)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
//                mainRecords.filterRecords()
                binding.verticalRecycleView.adapter!!.notifyDataSetChanged()
                binding.horizontalRecycleView.visibility = View.VISIBLE
                binding.verticalRecycleView.visibility = View.VISIBLE
                binding.searchViewInfoLayout.visibility = View.GONE
                binding.verticalRefresh.isEnabled = true
                return true
            }
        })

        val searchView: androidx.appcompat.widget.SearchView = menuItem.actionView as androidx.appcompat.widget.SearchView

        searchView.queryHint = getString(R.string.search_view_hint)

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (binding.horizontalRecycleView.visibility != View.VISIBLE){

                    if (newText!!.isEmpty()){
                        binding.verticalRecycleView.visibility = View.GONE
                        binding.searchViewInfoLayout.visibility = View.VISIBLE

                        binding.searchViewInfoTV.text = getString(R.string.empty_search_view_info)

                    }
                    else{
//                        mainRecords.filterRecords(newText)
//
//                        if (mainRecords.verticalRecords.isEmpty()){
//                            binding.verticalRecycleView.visibility = View.GONE
//                            binding.searchViewInfoLayout.visibility = View.VISIBLE
//
//                            binding.searchViewInfoTV.text = getString(R.string.not_found_search_view_info, newText)
//                        } else{
//                            binding.searchViewInfoLayout.visibility = View.GONE
//                            binding.verticalRecycleView.visibility = View.VISIBLE
//                            binding.verticalRecycleView.adapter!!.notifyDataSetChanged()
//                        }
//

                    }

                }

                return true
            }
        })



        return super.onCreateOptionsMenu(menu)
    }
}

