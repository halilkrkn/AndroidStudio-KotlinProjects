package com.example.retrofitapp.service

import retrofit2.http.GET

interface RetrofitAPI {

    //GET = Serverdan veri çekme, POST = Servera veri eklemek, UPDATE = Serverda güncelleştirme yapmak için, DELETE = Serverdan veriyi silmek için
    //Api Key : 86807b574836f0859c6c4b6e2f1f43c0


    //Base = https://api.nomics.com/v1
    // prices?key=86807b574836f0859c6c4b6e2f1f43c0
    @GET("prices?key=86807b574836f0859c6c4b6e2f1f43c0")

}