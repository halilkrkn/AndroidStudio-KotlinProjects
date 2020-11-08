package com.example.workmanager.uı

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.workmanager.R
import com.example.workmanager.worker.RefreshDatabase
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Work Manager:
        //  App kapalıyken, arkaplanda çalışırken ve app hiç çalışmasa bile app arkada planda çalışmasını sağlatır.
        // Örnek olarak bir veritabanını güncelleme, KOnum bilgisini alma işlemleri gibi
        // Uygulamanın arka planda belli isteklerimiz doğrultusunda çalışmasını sağlayan bir Jetpack Sınıfı.
        // Bu sayade uygulqmadan çıksak bile arka planda yine verilerimizi güncel tutmamı veya mesela 15 dk bir konum bilgisini almak için
        // yada sadece wifi açık olduğunda, batarya şarjdeyken gibi yapılmasını istediğimiz durumlar için kullanılır.
        // Her work e kendine ait bir ID atanıyor.

        val data = Data.Builder()
            .putInt("intKey", 1)
            .build()

        // Work işlemlerini istediğimiz formatta yapabilmemize yardımcı oluyor.
        // Mesela Wifi Açıkken ya da Teleefon Şarjdayken work işlemlerini gerçekleştir diyebiliriz.
        val constraints = Constraints.Builder()
            //Telefon Şarjdayken mi yoksa şarjda değilken mi ve şarjda değilse
            .setRequiresCharging(false)
            .build()
            // Ağa Bağlı mı değil mi iken ve Bağlı ise
           // .setRequiredNetworkType(NetworkType.CONNECTED)


          /*
          ** OneTimeWorkRequestBuilder<>() **
          Bir defaya mahsus bir workrequest bir defaya mahsus çalıştırılıyor.
          App i ilk açtıktan sonra 25 dk sonra şu işlemleri yap komutunu verebiliriz


         ** PeriodicWorkRequestBuilder<>() **
         Genel olrak bu Fonksiyon kullanılır.
         Kaç dakika saat zaman sonra yapmamız gereken işlemlerde kullanırız.
         Workrequesti istediğimiz bir süre içerisnde çalıştırmamızı sağlıyor.
         Mesela 15 dk da bir çalıştır gibi
           */

//        val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
//            //oluşturduğumuz constraints burada tanımlayabiliyoruz.
//            .setConstraints(constraints)
//            //oluşturduğumuz datayı burada tanımlayabiliyoruz.
//            // Yani elimizde veri varsa bu istekle beraber yolla diyip yollayabilirz.
//            .setInputData(data)
//            //Gecikmeli olarak başlamk için istediğimiz zaman aralığında yapabilmemize yardımcı oluyor.
//           // .setInitialDelay(5,TimeUnit.MINUTES)
//            //Birden fazla work manager olabilir o yüzden kendmiz özel olara ekitek(tag) atayabiliriz.
//            //.addTag("myTag")
//            .build()
//
//        WorkManager.getInstance(this).enqueue(myWorkRequest)

         //En az bir işlem 15 dk bir yapılıyor. Genel bir kural.
        val myWorkRequest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        // Uygulamamız çalıştırdık ve 15 dk bir veri değişimi gözlemleyecez.
        // Veriyi gözlemleyebilmek için Logcat a system.out yazıp ordan takip ediyoruz.
        WorkManager.getInstance(this).enqueue(myWorkRequest)

         // WorkManager Detayları
          //WorkManager da work işlemimizi takip etmek için
         WorkManager.getInstance(this)
             .getWorkInfoByIdLiveData(myWorkRequest.id)
             .observe(this,
                 Observer {
                 if (it.state == WorkInfo.State.RUNNING){
                     println("Running")
                 }else if (it.state == WorkInfo.State.FAILED){
                     println("Failed")
                 }else if (it.state == WorkInfo.State.SUCCEEDED){
                     println("Succeeded")
                 }
             })

        // Work işlemlerini iptal edebiliriz.
//         WorkManager.getInstance(this).cancelAllWork()


         // Chaining İşlemleri
        // Yani arka arkaya  sırasıyla bu işlemler yapabiliriz.
        // Sadece  OneTimeWorkRequestBuilder da yapılabiliyor.
        
        val oneTimeWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        // Burda ilk önce beginWith ile başla sonra bittikten sonra then lerin içindekilere başla böyle böyle sırasıyla zincirleme tekniği ile işlemler yaptırabiliriz.
        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .enqueue()

        
    }
}