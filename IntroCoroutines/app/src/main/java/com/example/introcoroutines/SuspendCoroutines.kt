package com.example.introcoroutines

import kotlinx.coroutines.*

fun main(){
/*
    GlobalScope.launch {
        myfunction()
    }
     Burda da çalıştırlabilir.
 */
    runBlocking {
        delay(2000)
        println("run blocking")
        myfunction()
    }
}

// Suspend Coroutines Fonksiyon = İçerisinde coroutine çalıştırlabilen Fonksiyonlardır.
// İstediğimiz zaman durdurup devam edebildiğimiz fonk.
suspend fun myfunction(){
    coroutineScope {
        delay(4000)
        println("suspend Function")
    }
}