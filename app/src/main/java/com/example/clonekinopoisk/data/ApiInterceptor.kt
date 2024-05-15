package com.example.clonekinopoisk.data

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("X-API-KEY", "55558c39-b505-4c5f-b6cd-5c91824884b7")
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(modifiedRequest)
    }
}
// 1 key - 55558c39-b505-4c5f-b6cd-5c91824884b7
// 2 key - 94465803-dd38-480b-a973-9f3b0062aec9