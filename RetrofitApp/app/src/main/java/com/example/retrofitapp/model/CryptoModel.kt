package com.example.retrofitapp.model

// data class = İçerisine tamamen veriyi çekeceğimiz ve bu verileri işleyeceğimiz bir yapı oluştumak içindir.
data class CryptoModel(
    // burdaki currency ve price veri çekeceğimiz datanın içindeki değerleri yazdık.
    // @SerializedName("currency") // java bu methodu yazmamıza gerek var
    val currency: String,
    // @SerializedName("price")
    val price: String ) // Constructor oluşturuldu