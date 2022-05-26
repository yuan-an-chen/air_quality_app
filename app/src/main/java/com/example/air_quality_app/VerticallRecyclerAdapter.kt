package com.example.air_quality_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
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

        when(records[position].status){
            "普通" -> itemBinding.vItemStatusTv.setTextColor(Color.parseColor("#b3b002"))
            "對敏感族群不健康" -> itemBinding.vItemStatusTv.setTextColor(Color.parseColor("#ff9933"))
            "對所有族群不健康" -> itemBinding.vItemStatusTv.setTextColor(Color.parseColor("#cc0033"))
            "非常不健康" -> itemBinding.vItemStatusTv.setTextColor(Color.parseColor("#660099"))
            "危害" -> itemBinding.vItemStatusTv.setTextColor(Color.parseColor("#7e0023"))
        }

        if (records[position].status.length > 8){
            itemBinding.vItemStatusTv.setTextColor(Color.parseColor("#009966"))
            itemBinding.vItemArrowTv.visibility = View.GONE
        } else
            itemBinding.vItemArrowTv.visibility = View.VISIBLE

        itemBinding.vItemStatusTv.text = records[position].status
    }


}

class VerticalViewHolder(val itemViewBinding: VerticalItemLayoutBinding): RecyclerView.ViewHolder(itemViewBinding.root) {}