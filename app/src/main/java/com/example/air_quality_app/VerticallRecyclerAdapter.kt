package com.example.air_quality_app

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.air_quality_app.databinding.VerticalItemLayoutBinding

class VerticalRecyclerAdapter(): RecyclerView.Adapter<VerticalRecyclerAdapter.VerticalViewHolder>() {

    private var records: List<Record> = listOf()

    fun submitList(newList: List<Record>){
        records = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val itemLayoutItem = VerticalItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalViewHolder(itemLayoutItem, parent.context)
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

    inner class VerticalViewHolder(val itemViewBinding: VerticalItemLayoutBinding, mainContext: Context): RecyclerView.ViewHolder(itemViewBinding.root){
        init {

            itemViewBinding.root.setOnClickListener {
                if (records[bindingAdapterPosition].status.length < 10)
                    Toast.makeText(mainContext, "The PM 2.5 reading of ${records[bindingAdapterPosition].siteName} is ${records[bindingAdapterPosition].reading}.", Toast.LENGTH_LONG).show()

            }

        }

    }
}

