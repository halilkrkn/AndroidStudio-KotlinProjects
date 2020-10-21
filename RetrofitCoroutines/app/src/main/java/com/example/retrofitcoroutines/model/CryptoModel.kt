package com.example.retrofitcoroutines.model

import com.google.gson.annotations.SerializedName

data class CryptoModel
    (
    // burdaki currency ve price veri çekeceğimiz datanın içindeki değerleri yazdık.
    @SerializedName("currency") // java bu methodu yazmamıza gerek var
    val currency: String,
    @SerializedName("price")
    val price: String ) // Constructor oluşturuldu
