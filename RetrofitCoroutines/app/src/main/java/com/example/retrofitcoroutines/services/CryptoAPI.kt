package com.example.retrofitcoroutines.services

import com.example.retrofitcoroutines.model.CryptoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {
    @GET("halilkrkn/AndroidStudio-KotlinProjects/master/RetrofitApp/RetrofitJson/Retrofit.json")
    suspend fun getData(): Response<List<CryptoModel>>
}