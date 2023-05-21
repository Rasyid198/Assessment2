package org.d3if4075.Persegiku

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if4075.Persegiku.data.BangunDatar
import org.d3if4075.Persegiku.network.ApiStatus
import org.d3if4075.Persegiku.network.BangunDatarApi

class ListBangunDatarViewModel: ViewModel() {
    private val data = MutableLiveData<List<BangunDatar>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData(){
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            try {
                //val result = BangunRuangApi.service.getBangunRuang()
                //Log.d("ListBangunRuangVM", "Success: $result")
                data.value = BangunDatarApi.service.getBangunDatar()
                status.value = ApiStatus.SUCCESS
            }catch (e: Exception){
                Log.d("ListBangunDatarVM", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun getData(): LiveData<List<BangunDatar>> = data

    fun getStatus(): LiveData<ApiStatus> = status

}