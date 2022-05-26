package com.example.air_quality_app

class Records(var apiResponse: APIResponse) {

    var horizontalRecords: ArrayList<Record> = ArrayList()
    var verticalRecords: ArrayList<Record> = ArrayList()

    fun filterRecords(threshold: Int = 5) {
        horizontalRecords.clear()
        verticalRecords.clear()
        for (record in apiResponse.records) {
            val newRecord: Record = record.copy()

            if (record.reading.isEmpty()){
                newRecord.reading = "設備異常"
            }

            if (newRecord.reading == "設備異常" || record.reading.toInt() > threshold){
                if (record.status == "良好")
                    newRecord.status = "The status is good, we want to go out to have fun"
                verticalRecords.add(newRecord)
            } else{
                horizontalRecords.add(newRecord)
            }

        }

    }

    fun filterRecords(searchWord: String) {
        verticalRecords.clear()
        for (record in apiResponse.records) {
            if (record.siteName.contains(searchWord) || record.county.contains(searchWord)){
                val newRecord: Record = record.copy()
                if (record.reading.isEmpty())
                    newRecord.reading = "設備異常"

                if (record.status == "良好")
                    newRecord.status = "The status is good, we want to go out to have fun"
                verticalRecords.add(newRecord)

            }
        }

    }
}