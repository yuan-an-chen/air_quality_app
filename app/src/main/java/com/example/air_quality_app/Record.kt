package com.example.air_quality_app

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class Record(

    @SerializedName("siteid")
    val siteId: String,

    @SerializedName("pm2.5")
    var reading: String,

    @SerializedName("county")
    val county: String,

    @SerializedName("sitename")
    val siteName: String,

    @SerializedName("status")
    var status: String
)

data class APIResponse(var records: List<Record>){}


object RecordDiffCallback : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem.siteId == newItem.siteId
    }
}