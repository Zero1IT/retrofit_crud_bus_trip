package com.example.service.services

import retrofit2.Response

class ResponseException(private val response: Response<*>) : Throwable() {
    override val message: String?
        get() = response.raw().networkResponse()?.code()?.toString() ?: "Undefined error"
}
