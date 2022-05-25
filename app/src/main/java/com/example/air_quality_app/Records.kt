package com.example.air_quality_app

class Records(var apiResponse: APIResponse) {

    var horizontalRecords: ArrayList<Record> = ArrayList()
    var verticalRecords: ArrayList<Record> = ArrayList()

    fun filterRecords(threshold: Int = 5) {
        horizontalRecords.clear()
        verticalRecords.clear()
        for (record in apiResponse.records) {
            if (record.reading.toInt() <= threshold)
                horizontalRecords.add(record)
            else
                verticalRecords.add(record)
        }

    }

    fun filterRecords(searchWord: String) {
        verticalRecords.clear()
        for (record in apiResponse.records) {
            if (record.siteName.contains(searchWord) || record.county.contains(searchWord))
                verticalRecords.add(record)
        }

    }
}