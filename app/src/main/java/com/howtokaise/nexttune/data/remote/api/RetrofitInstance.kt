package com.howtokaise.nexttune.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://nexttune.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api : ApiService = retrofit.create(ApiService::class.java)
}