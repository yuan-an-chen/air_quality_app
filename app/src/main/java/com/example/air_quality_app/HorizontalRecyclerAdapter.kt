package com.example.air_quality_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.air_quality_app.databinding.HorizontalCardLayoutBinding

class HorizontalRecyclerAdapter(): androidx.recyclerview.widget.ListAdapter<Record, HorizontalRecyclerAdapter.HorizontalViewHolder>(RecordDiffCallback)  {

    inner class HorizontalViewHolder(private val itemViewBinding: HorizontalCardLayoutBinding): RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(record: Record){
            itemViewBinding.hCardIdxTv.text = record.siteId
            itemViewBinding.hCardCountyTv.text = record.county
            itemViewBinding.hCardSiteTv.text = record.siteName

            if (record.status == "")
                itemViewBinding.hCardStatusTv.text = "無數據"
            else
                itemViewBinding.hCardStatusTv.text = record.status

            if (record.reading == "")
                itemViewBinding.hCardReadingTv.text = "無數據"
            else
                itemViewBinding.hCardReadingTv.text = record.reading


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val cardLayoutItem = HorizontalCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HorizontalViewHolder(cardLayoutItem)
    }


    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

