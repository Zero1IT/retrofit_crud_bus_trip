package com.example.service.services

import com.example.service.beans.BusTrip
import retrofit2.Call
import retrofit2.http.*

interface BusTripRestApi {
    @GET("bus")
    fun getAllBusTrips(): Call<List<BusTrip>>
    @GET("bus/{id}")
    fun getBusTripById(@Path("id") id: Long): Call<BusTrip>
    @DELETE("bus/{id}")
    fun deleteBusTrip(@Path("id") id: Long): Call<BusTrip>
    @POST("bus")
    fun createBusTrip(@Body busTrip: BusTrip): Call<Unit>
    @PUT("bus")
    fun updateBusTrip(@Body busTrip: BusTrip): Call<BusTrip>
}