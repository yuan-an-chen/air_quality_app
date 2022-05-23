package com.example.air_quality_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hRecyclerView: RecyclerView = findViewById(R.id.horizontal_recycle_view)

        val hLayoutManager = LinearLayoutManager(this)
        hLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        hRecyclerView.layoutManager = hLayoutManager



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