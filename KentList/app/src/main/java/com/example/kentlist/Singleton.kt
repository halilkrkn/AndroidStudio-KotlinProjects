package com.example.kentlist

import android.graphics.Bitmap

// Tek bir objesi olacan sınıf demektir Singleton
class Singleton {

    // bu durum herhangi bir sınıfta kullandığımızda artık diğer tüm sınıflarda da aynı şekilde kullanmamıza  o sınıfın özelliklerine ulaşılabiliyor.
    companion object selected{
        var selectedImage: Bitmap? = null

    }

}