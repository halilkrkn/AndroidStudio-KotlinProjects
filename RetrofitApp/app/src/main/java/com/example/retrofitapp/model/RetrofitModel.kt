package com.example.retrofitapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

// data class = İçerine tamamen veriyi çekeceğimiz ve bu verileri işleyeceğimiz bir yapı oluştumak içindir
data class RetrofitModel(
    // burdaki currency ve price veri çekeceğimiz datanın içindeki değerleri yazdık.
  //  @SerializedName("currency") // java bu methodu yazmamıza gerek var
    val currency: String,
   // @SerializedName("price")
    val price: String )// Constructor oluşturduk.
