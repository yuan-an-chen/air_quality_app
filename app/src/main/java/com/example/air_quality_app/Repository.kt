package com.example.air_quality_app

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class Repository private constructor(){

    val dataFlow: Flow<List<Record>> = flow{
        emit(fetchData())

        // fetch for every 10 minutes
        while (true){
            delay(600000)
//            delay(5000)
            emit(fetchData())
        }
    }

    suspend fun fetchData(): List<Record>{
        val retroInstance = RetrofitInstance.instance.create(APIService::class.java)
        val response = retroInstance.fetchData()
        return response.records
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(): Repository {
            return synchronized(Repository::class) {
                val newInstance = instance ?: Repository()
                instance = newInstance
                newInstance
            }
        }
    }
}