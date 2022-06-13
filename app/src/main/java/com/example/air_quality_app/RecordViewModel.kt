package com.example.air_quality_app

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class RecordViewModel(): ViewModel() {

    private var records = listOf<Record>()

    private var _horizontalListLiveData = MutableLiveData<List<Record>>()
    val horizontalListLiveData: LiveData<List<Record>>
        get() = _horizontalListLiveData

    private var _verticalListLiveData = MutableLiveData<List<Record>>()
    val verticalListLiveData: LiveData<List<Record>>
        get() = _verticalListLiveData

    private val searchTrigger = MutableLiveData<String>("")

    val searchListLiveData: LiveData<List<Record>> = searchTrigger.switchMap {
        val resultRecords: ArrayList<Record> = ArrayList()

        if (it.isNotEmpty()){
            for (record in records) {
                if (record.siteName.contains(it) || record.county.contains(it)){
                    val newRecord: Record = record.copy()
                    if (record.reading.isEmpty())
                        newRecord.reading = "設備異常"

                    if (record.status == "良好")
                        newRecord.status = "The status is good, we want to go out to have fun"
                    resultRecords.add(newRecord)
                }
            }
        }

        MutableLiveData<List<Record>>(resultRecords)
    }

    fun searchFor(searchWord: String) {
        searchTrigger.value = searchWord
    }

    init {
        // listen for data change in repository via flow
        viewModelScope.launch(Dispatchers.IO) {
            Repository.getInstance().dataFlow.collectLatest { recordList->
                records = recordList
                filterRecords()
            }
        }
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            records = Repository.getInstance().fetchData()
            filterRecords()
        }
    }

    fun filterRecords(threshold: Int = 5) {
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
        searchTrigger.postValue(searchTrigger.value)
    }


}
