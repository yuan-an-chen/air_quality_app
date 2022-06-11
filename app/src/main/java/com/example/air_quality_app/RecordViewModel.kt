package com.example.air_quality_app

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class RecordViewModel(): ViewModel() {

    private var _horizontalListLiveData = MutableLiveData<List<Record>>()
    val horizontalListLiveData: LiveData<List<Record>>
        get() = _horizontalListLiveData

    private var _verticalListLiveData = MutableLiveData<List<Record>>()
    val verticalListLiveData: LiveData<List<Record>>
        get() = _verticalListLiveData

    init {
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            Repository.getInstance().dataFlow.collectLatest { recordList->
                filterRecords(recordList)
            }
        }
    }

    fun filterRecords(records: List<Record>, threshold: Int = 5) {
        val horizontalRecords: ArrayList<Record> = ArrayList()
        val verticalRecords: ArrayList<Record> = ArrayList()

        for (record in records) {
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

        _horizontalListLiveData.postValue(horizontalRecords)
        _verticalListLiveData.postValue(verticalRecords)
    }


}
