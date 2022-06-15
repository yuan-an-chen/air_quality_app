package com.example.air_quality_app

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.air_quality_app.databinding.VerticalItemLayoutBinding

class VerticalRecyclerAdapter(): androidx.recyclerview.widget.ListAdapter<Record, VerticalRecyclerAdapter.VerticalViewHolder>(RecordDiffCallback) {

    inner class VerticalViewHolder(
        private val itemViewBinding: VerticalItemLayoutBinding,
        private val mainContext: Context): RecyclerView.ViewHolder(itemViewBinding.root){

        var currentRecord: Record? = null

        init {

            itemViewBinding.root.setOnClickListener {
                if (currentRecord != null && currentRecord?.status != "良好")
                    Toast.makeText(mainContext, "The PM 2.5 reading of ${currentRecord?.siteName} is ${currentRecord?.reading}.", Toast.LENGTH_LONG).show()
            }

        }

        fun bind(record: Record){
            currentRecord = record

            itemViewBinding.vItemIdxTv.text = record.siteId
            itemViewBinding.vItemCountyTv.text = record.county
            itemViewBinding.vItemSiteTv.text = record.siteName
            itemViewBinding.vItemStatusTv.text = record.status

            if (record.reading == "")
                itemViewBinding.vItemReadingTv.text = "無數據"
            else
                itemViewBinding.vItemReadingTv.text = record.reading

            itemViewBinding.vItemArrowTv.visibility = View.VISIBLE

            when(record.status){
                "良好" -> {
                    itemViewBinding.vItemStatusTv.setTextColor(Color.parseColor("#009966"))
                    itemViewBinding.vItemArrowTv.visibility = View.GONE
                    itemViewBinding.vItemStatusTv.text = mainContext.getString(R.string.status_good_message)
                }
                "普通" -> itemViewBinding.vItemStatusTv.setTextColor(Color.parseColor("#b3b002"))
                "對敏感族群不健康" -> itemViewBinding.vItemStatusTv.setTextColor(Color.parseColor("#ff9933"))
                "對所有族群不健康" -> itemViewBinding.vItemStatusTv.setTextColor(Color.parseColor("#cc0033"))
                "非常不健康" -> itemViewBinding.vItemStatusTv.setTextColor(Color.parseColor("#660099"))
                "危害" -> itemViewBinding.vItemStatusTv.setTextColor(Color.parseColor("#7e0023"))
                else -> {
                    itemViewBinding.vItemStatusTv.text = "無數據"
                    itemViewBinding.vItemStatusTv.setTextColor(Color.parseColor("#000000"))
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val itemLayoutItem = VerticalItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalViewHolder(itemLayoutItem, parent.context)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

