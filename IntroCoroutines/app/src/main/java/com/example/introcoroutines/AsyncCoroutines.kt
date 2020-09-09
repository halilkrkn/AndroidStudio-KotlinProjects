package com.example.introcoroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    //Async Coroutines
    // Örneğin internetten iki tane veri çekiyoruz ve bunları ne zaman indireceğimiz belli olmaybilir.
    // Yani aynı anda nasıl indirecez veya veriyi indirip bekleyelim öyle mi işlem yapalım bunun nasıl yapcağımız

    var userName = ""
    var userAge = 0

    runBlocking {
        val downloadedName = async {
            downloadName()
        }
        val downloadedAge = async {
            downloadAge()
        }
        userName = downloadedName.await()
        userAge = downloadedAge.await()

        println("${userName} ${userAge}")

    }

}

suspend fun downloadName(): String{
    delay(2000)
    val userName = "Halil: "
    println("username download")
    return userName
}
suspend fun downloadAge(): Int {
    delay(4000)
    val userAge = 60
    println("userage download")
    return userAge
}