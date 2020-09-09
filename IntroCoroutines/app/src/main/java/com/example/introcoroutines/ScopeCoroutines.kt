package com.example.introcoroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    runBlocking { launch {
        delay(5000)
        println("runblocking")
    }
    coroutineScope {
    launch {
        delay(3000)
        println("coroutine scope ")
    }
    }
    }
}