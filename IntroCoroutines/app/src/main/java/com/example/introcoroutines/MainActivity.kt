package com.example.introcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Coroutine = Light Thread denilebilir.
        //Light Wightness
        /*
        GlobalScope.launch {
            repeat(100000){
                launch {
                    println("android")
                }
            }
        }


         */
        // Scope = Kapsam
        // Global Scope = Bütün app deki kapsam
        // runBlocking =  Tek bir coroutine çalıştırılacaksa yapılır.
        // CoroutineScope
/*
        //runBlocking
        println("run blockin start")
        runBlocking {
            //launch {} cyeni bir coroutine başlatır.
            launch {
                delay(5000)
                println("run blocking")
            }
        }
        println("run blocking end")
 */
        /*
        //Global Scope
        println("Global scope start")
        GlobalScope.launch {
            delay(5000)
            println("global scope")
        }
        println("global scope end")

         */

/*
        // CoroutineScope
        println("coroutine scope start")
        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            println("coroutine scope")
        }
        println("coroutine scope end")
 */
        /*
        // İç içe CoroutineScope
        runBlocking { launch {
            delay(5000)
            println("runblockinng")
        }
            coroutineScope {
                launch {
                    delay(3000)
                    println("coroutine scope ")
                }
            }
        }
      */
        /*
        //Dispatcher Coroutines
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

         */
        runBlocking {
            delay(2000)
            println("run blocking")
            myfunction()
        }
// Suspend Coroutines Fonksiyon = İçerisinde coroutine çalıştırlabilen Fonksiyonlardır.
// İstediğimiz zaman durdurup devam edebildiğimiz fonk.
suspend fun myfunction(){
    coroutineScope {
        delay(4000)
        println("suspend Function")
    }
}

    }

}