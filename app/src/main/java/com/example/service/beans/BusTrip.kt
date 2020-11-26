package com.example.service.beans

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusTrip(
        var id: Long,
        var destinationTitle: String,
        var routeNumber: Int,
        var departureTime: Time,
        var arrivalTime: Time
) : Parcelable {

    constructor() : this(0, "", 0, Time.of(0, 0), Time.of(0, 0)) {

    }
}