package com.example.retrofitapp.service

import com.example.retrofitapp.model.RetrofitModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {

    //GET = Serverdan veri çekme, POST = Servera veri eklemek, UPDATE = Serverda güncelleştirme yapmak için, DELETE = Serverdan veriyi silmek için
    //Api Key : 86807b574836f0859c6c4b6e2f1f43c0

    //Github hesabımdan eğer veriyi yüklemek istiyorsan;
    //Base = https://raw.githubusercontent.com/ view tarafında  MainActivity de retrofit objemizi yaparken kullanıcaz
    // halilkrkn/AndroidStudio-KotlinProjects/master/RetrofitApp/app/src/main/java/com/example/retrofitapp/retrofitJSON/Retrofit.json
    // @GET(" halilkrkn/AndroidStudio-KotlinProjects/master/RetrofitApp/app/src/main/java/com/example/retrofitapp/retrofitJSON/Retrofit.json")

    //Veriyi çekeceğimiz siteden yüklemek istiyorsak
    //Base = https://api.nomics.com/v1 view tarafında  MainActivity de retrofit objemizi yaparken kullanıcaz
    // prices?key=86807b574836f0859c6c4b6e2f1f43c0


    //Call<> = İnternetten Async bir şekilde verileri indirirken yani main Threadi bloklamadan kullanıcın arayüzünü bloklamadan yapacağımız işlem.
    //Call<RetrofitModel>

    //Retrofit
    //@GET("prices?key=86807b574836f0859c6c4b6e2f1f43c0")
    //fun getData(): Call<List<RetrofitModel>>

    //Rxjava
    @GET("prices?key=86807b574836f0859c6c4b6e2f1f43c0")
    fun getData(): Observable<List<RetrofitModel>>



}