package com.example.service.services

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object RetrofitService {
    private const val URL = "http://192.168.43.35:8080/api/"//"http://192.168.100.5:8080/api/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val busTripApi: BusTripRestApi = retrofit.create(BusTripRestApi::class.java)
}

suspend inline fun <reified T> Call<T>.await(): T = suspendCoroutine { continuation -> enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                continuation.resume(response.body()!!)
            } else {
                continuation.resumeWithException(ResponseException(response))
            }
        }
    })
}