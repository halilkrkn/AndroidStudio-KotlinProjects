package com.example.introcoroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main(){

    // Dispatchers = coroutine leri farklı threadlerde çalıştırmamıza olanak sağlıyor.
    // Dispatchers.Default -> CPU Intensive = işlemciyi çok kullanımında yapılan işlem
    // Dispatchers.IO -> Input/Output = Networking İşlemleri, İnternetten veri çekmek veya Veritabanından  veri almak için bunları IO ile yapılıyor.
    // Dispatchers.Main -> UI = main thread ve kullanıcının arayüzü ve kullanıcın görebileceği işlemleri mainde yapmalıyız.
    // Dispatchers.Unconfined -> Nerede oluşturulduysa inherite ediliyor. Yani miras alıyor.


    runBlocking {
        launch(Dispatchers.Main) {
            println("Main Thread: ${Thread.currentThread().name}")
        }
        launch(Dispatchers.IO) {
            println("IO Thread: ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            println("Default Thread: ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {
            println("Unconfined Thread: ${Thread.currentThread().name}")
        }

    }




}