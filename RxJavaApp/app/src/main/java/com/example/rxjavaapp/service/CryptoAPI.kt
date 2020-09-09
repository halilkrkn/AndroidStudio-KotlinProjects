package com.example.rxjavaapp.service


import com.example.rxjavaapp.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    @GET("/prices?key=86807b574836f0859c6c4b6e2f1f43c0")
    fun getData(): Observable<List<CryptoModel>>
}


