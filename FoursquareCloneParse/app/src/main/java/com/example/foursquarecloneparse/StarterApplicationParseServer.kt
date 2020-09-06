package com.example.foursquarecloneparse

import android.app.Application
import com.parse.Parse

//Oluşturduğumuz StarterApplicationParseServer class ını manifestte application içerine name olarak tanıttık.
class StarterApplicationParseServer: Application() {

    //Parse Ayarları
    override fun onCreate() {
        super.onCreate()

        //setLogLevel ile parse dair debug işlemlerini logcat görmemize yardımcı oluyor.
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)

        //Bir Sunucu oluşturduğumuzda  ihtiyacımız olan durumlar ile initialize ediyoruz ve bu şekilde bu projemizi diğer bilgisayarla haberleşmeyi sağlatıyoruz.
        // Sunucu oluşturmak için ise hazır parse kodlarını barındıran BackForApp sunucusunu kullanıcaz ve beleş :). Amazon Web Server (AWS) kullanılabilir.
        // BackForApp e üye olup Core Settings Kısmında bulunan app Id, clientKey ve server adress i buraya ekliyoruz.
        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId("oiBmeQZ8GCHVnYLcXUQlbTCNkIsd0ogHyc8csP3E")
            .clientKey("tkxxzGiAoCEEf2hWtoi62j6o8dofbSGrmroA9ZQ0")
            .server("https://parseapi.back4app.com/")
            .build())
    }


}