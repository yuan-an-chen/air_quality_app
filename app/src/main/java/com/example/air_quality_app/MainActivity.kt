package com.example.air_quality_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.air_quality_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<RecordViewModel>()

    private val horizontalRecyclerAdapter = HorizontalRecyclerAdapter()
    private val verticalRecyclerAdapter = VerticalRecyclerAdapter()
    private val searchRecyclerAdapter = VerticalRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchViewInfoLayout.visibility = View.GONE
        title = getString(R.string.app_title)

        // setup layout manager and adapter for horizontalRecycleView
        binding.horizontalRecycleView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.horizontalRecycleView.adapter = horizontalRecyclerAdapter

        // listen for horizontalListLiveData changes to update horizontalRecycleView
        mainViewModel.horizontalListLiveData.observe(this) {
            horizontalRecyclerAdapter.submitList(it)
            horizontalRecyclerAdapter.notifyDataSetChanged()
            binding.verticalRefresh.isRefreshing = false
        }

        // setup layout manager and adapter for verticalRecycleView
        binding.verticalRecycleView.layoutManager = LinearLayoutManager(this)
        binding.verticalRecycleView.adapter = verticalRecyclerAdapter

        // listen for verticalListLiveData changes to update verticalRecycleView
        mainViewModel.verticalListLiveData.observe(this) {
            verticalRecyclerAdapter.submitList(it)
            verticalRecyclerAdapter.notifyDataSetChanged()
            binding.verticalRefresh.isRefreshing = false
        }

        binding.searchRecycleView.layoutManager = LinearLayoutManager(this)
        binding.searchRecycleView.adapter = searchRecyclerAdapter

        mainViewModel.searchListLiveData.observe(this) {
            searchRecyclerAdapter.submitList(it)
            searchRecyclerAdapter.notifyDataSetChanged()
        }

        binding.verticalRefresh.setOnRefreshListener {
            mainViewModel.fetchData()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.toobar_menu, menu)

        val menuItem: MenuItem = menu.findItem(R.id.search_item)

        menuItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                binding.mainDisplayLayout.visibility = View.GONE
                binding.searchViewInfoLayout.visibility = View.VISIBLE

                binding.verticalRefresh.isEnabled = false
                mainViewModel.searchFor("")
                binding.searchViewInfoTV.text = getString(R.string.empty_search_view_info)

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {

                mainViewModel.filterRecords()
                binding.mainDisplayLayout.visibility = View.VISIBLE
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

                if (binding.mainDisplayLayout.visibility != View.VISIBLE){

                    mainViewModel.searchFor(newText!!)

                    if (newText.isEmpty()){
                        binding.searchViewInfoTV.text = getString(R.string.empty_search_view_info)
                    }
                    else{
                        binding.searchViewInfoTV.text = ""

                        if (searchRecyclerAdapter.itemCount == 0){
                            binding.searchViewInfoTV.text = getString(R.string.not_found_search_view_info, newText)
                        }

                    }
                }
                return true
            }
        })



        return super.onCreateOptionsMenu(menu)
    }
}

