package com.example.air_quality_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.air_quality_app.databinding.HorizontalCardLayoutBinding

class HorizontalRecyclerAdapter(): RecyclerView.Adapter<HorizontalViewHolder>() {

    private var records: List<Record> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val cardLayoutItem = HorizontalCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HorizontalViewHolder(cardLayoutItem)
    }

    fun submitList(newList: List<Record>){
        records = newList
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        val itemBinding = holder.itemViewBinding
        itemBinding.hCardIdxTv.text = records[position].siteId
        itemBinding.hCardReadingTv.text = records[position].reading
        itemBinding.hCardCountyTv.text = records[position].county
        itemBinding.hCardSiteTv.text = records[position].siteName
        itemBinding.hCardStatusTv.text = records[position].status
    }


}

class HorizontalViewHolder(val itemViewBinding: HorizontalCardLayoutBinding): RecyclerView.ViewHolder(itemViewBinding.root) {}