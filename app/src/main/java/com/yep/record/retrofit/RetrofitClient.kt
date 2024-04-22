package com.yep.record.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val baseUri = "http://127.0.0.1/8089/"/*10.0.2.2*/

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUri)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val loginApi by lazy {
        retrofit.create(ApiService::class.java)
    }


}