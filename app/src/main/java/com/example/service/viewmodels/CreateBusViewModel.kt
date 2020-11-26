package com.example.service.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.service.beans.BusTrip
import com.example.service.services.RetrofitService
import com.example.service.services.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CreateBusViewModel(busTrip: BusTrip?, application: Application) : AndroidViewModel(application) {

    val initialized = busTrip != null
    var busTrip: BusTrip = busTrip ?: BusTrip()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main.plus(job))

    private val _createSuccessful = MutableLiveData<Boolean>()
    val createSuccessful: LiveData<Boolean> = _createSuccessful
    private val _updateSuccessful = MutableLiveData<Boolean>()
    val updateSuccessful: LiveData<Boolean> = _updateSuccessful

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun onCreateBusTrip() {
        uiScope.launch {
            if (initialized) {
                _updateSuccessful.value = acceptUpdateBus()
            } else {
                _createSuccessful.value = acceptCreateBus()
            }
        }
    }

    private suspend fun acceptCreateBus(): Boolean {
        if (tripIsCorrect()) {
            RetrofitService.busTripApi.createBusTrip(busTrip).await()
            return true
        }
        return false
    }

    private suspend fun acceptUpdateBus(): Boolean {
        if (tripIsCorrect()) {
            RetrofitService.busTripApi.updateBusTrip(busTrip).await()
            return true
        }
        return false
    }

    private fun tripIsCorrect(): Boolean {
        return busTrip.destinationTitle.isNotBlank()
                && busTrip.routeNumber > 0
                && busTrip.arrivalTime.correct
                && busTrip.departureTime.correct
    }
}