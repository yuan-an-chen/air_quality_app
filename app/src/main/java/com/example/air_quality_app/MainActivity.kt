package com.example.air_quality_app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.air_quality_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<RecordViewModel>()

    private val horizontalRecyclerAdapter = HorizontalRecyclerAdapter()
    private val verticalRecyclerAdapter = VerticalRecyclerAdapter()
    private val searchRecyclerAdapter = VerticalRecyclerAdapter()

    private var currentSearchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchViewInfoLayout.visibility = View.GONE
        title = getString(R.string.app_title)

        // setup layout manager and adapter for horizontalRecycleView
        binding.horizontalRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)
        binding.horizontalRecyclerView.adapter = horizontalRecyclerAdapter

        // listen for horizontalListLiveData changes to update horizontalRecycleView
        mainViewModel.horizontalListLiveData.observe(this) {
            horizontalRecyclerAdapter.submitList(it)
            binding.verticalRefresh.isRefreshing = false
        }

        // setup layout manager and adapter for verticalRecycleView
        binding.verticalRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.verticalRecyclerView.adapter = verticalRecyclerAdapter

        // listen for verticalListLiveData changes to update verticalRecycleView
        mainViewModel.verticalListLiveData.observe(this) {
            verticalRecyclerAdapter.submitList(it)
            binding.verticalRefresh.isRefreshing = false
        }

        binding.searchRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchRecyclerView.adapter = searchRecyclerAdapter

        // scroll to top when list is updated
        searchRecyclerAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                (binding.searchRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(positionStart, 0)
            }
        })

        mainViewModel.searchListLiveData.observe(this) {

            if (currentSearchText.isEmpty()){
                binding.searchViewInfoTV.text = getString(R.string.empty_search_view_info)
            }
            else{
                binding.searchViewInfoTV.text = ""

                if (it.isEmpty()){
                    binding.searchViewInfoTV.text = getString(R.string.not_found_search_view_info, currentSearchText)
                }

            }

            searchRecyclerAdapter.submitList(it)
        }

        // listen for swipe refresh
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

                    currentSearchText = newText!!
                    mainViewModel.searchFor(newText)

                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}

