package com.example.retrofitcoroutines.model

data class CryptoModel
    (
    // burdaki currency ve price veri çekeceğimiz datanın içindeki değerleri yazdık.
    // @SerializedName("currency") // java bu methodu yazmamıza gerek var
    val currency: String,
    // @SerializedName("price")
    val price: String ) // Constructor oluşturuldu
