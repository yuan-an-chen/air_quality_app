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

        MutableLiveData<List<Record>>(
            records.filter { record->
                it.isNotEmpty() && (record.siteName.contains(it) || record.county.contains(it))
            }
        )
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

    fun searchFor(searchWord: String) {
        searchTrigger.value = searchWord
    }

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            records = Repository.getInstance().fetchData()
            filterRecords()
        }
    }

    private fun filterRecords(threshold: Int = 5) {
        val horizontalRecords: ArrayList<Record> = ArrayList()
        val verticalRecords: ArrayList<Record> = ArrayList()

        for (record in records) {

            if (record.reading.isEmpty() || record.reading.toInt() > threshold){
                verticalRecords.add(record)
            } else{
                horizontalRecords.add(record)
            }

        }

        _horizontalListLiveData.postValue(horizontalRecords)
        _verticalListLiveData.postValue(verticalRecords)
        searchTrigger.postValue(searchTrigger.value)
    }


}
