package com.example.service.beans

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DataClassPrivateConstructor")
@Parcelize
data class Time private constructor(
    var hour: Int,
    var minute: Int
) : Parcelable {
    companion object {
        fun of(h: Int, m: Int): Time {
            if (h !in 0..60 && m !in 0..60) {
                throw IllegalArgumentException("Incorrect values of minutes($m) and hours($h)")
            }
            return Time(h, m)
        }
    }

    val correct
        get() = hour in 0..60 && minute in 0..60

    override fun toString(): String {
        return String.format("%02d:%02d", hour, minute)
    }
}