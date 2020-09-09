package com.example.introcoroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){

    //Job
    // launch çalıştırdığımız şeyleri job a eşitleyebiliyoruz
    // Yani job döndürüyor.
    runBlocking {
      val myJob =  launch {
            delay(2000)
            println("job")
          val secondJob = launch {
              println("job 2")
          }
        }
        myJob.invokeOnCompletion {
            println("my job end")
        }
        myJob.cancel()
    }

}