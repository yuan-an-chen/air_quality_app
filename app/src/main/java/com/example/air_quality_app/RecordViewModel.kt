package com.example.air_quality_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException



class RecordViewModel(private val repo: Repository): ViewModel() {

    private var apiResponse: APIResponse = APIResponse(listOf())

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
        val requestClient = repo.getClient()

        requestClient.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("failed to fetch data with error: $e")
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                println("onResponse......")
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                apiResponse = gson.fromJson(body, APIResponse::class.java)

                filterRecords()
            }

        })

    }

    fun filterRecords(threshold: Int = 5) {
        val horizontalRecords: ArrayList<Record> = ArrayList()
        val verticalRecords: ArrayList<Record> = ArrayList()

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

        _horizontalListLiveData.postValue(horizontalRecords)
        _verticalListLiveData.postValue(verticalRecords)
    }


}

class RecordViewModelFactory(private val repo: Repository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecordViewModel(repo) as T
    }
}