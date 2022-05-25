package com.example.air_quality_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.air_quality_app.databinding.VerticalItemLayoutBinding

class VerticalRecyclerAdapter(val records: List<Record>): RecyclerView.Adapter<VerticalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val itemLayoutItem = VerticalItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalViewHolder(itemLayoutItem)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val itemBinding = holder.itemViewBinding
        itemBinding.vItemIdxTv.text = records[position].siteId
        itemBinding.vItemReadingTv.text = records[position].reading
        itemBinding.vItemCountyTv.text = records[position].county
        itemBinding.vItemSiteTv.text = records[position].siteName
        itemBinding.vItemStatusTv.text = records[position].status
    }


}

class VerticalViewHolder(val itemViewBinding: VerticalItemLayoutBinding): RecyclerView.ViewHolder(itemViewBinding.root) {}