package com.example.service.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.service.beans.BusTrip
import com.example.service.beans.Time
import com.example.service.services.RetrofitService
import com.example.service.services.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListBusViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main.plus(job))

    private val _busTrips = MutableLiveData<List<BusTrip>>()
    val busTrips: LiveData<List<BusTrip>> = _busTrips
    private val _navigateToBusTrip = MutableLiveData<BusTrip>()
    val navigateToBusTrip: LiveData<BusTrip> = _navigateToBusTrip

    init {
        uiScope.launch {
            _busTrips.value = RetrofitService.busTripApi.getAllBusTrips().await()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun onBusItemClick(busTrip: BusTrip) {
        _navigateToBusTrip.value = busTrip
    }

    fun onBusTripNavigated() {
        _navigateToBusTrip.value = null
    }

    fun deleteItem(trip: BusTrip) {
        uiScope.launch {
            RetrofitService.busTripApi.deleteBusTrip(trip.id).enqueue(object : Callback<BusTrip> {
                override fun onResponse(call: Call<BusTrip>, response: Response<BusTrip>) {
                    // ignore
                }

                override fun onFailure(call: Call<BusTrip>, t: Throwable) {
                    // ignore
                }
            })
        }
    }
}