package com.example.oopkotlinproject

//Dynamic Polymorphism
class Dog : Animal() {
    fun test(){
        // super Kalıtım aldığımız Sınıfa referans verir.
        // Yani burda super.sing() ile Animal sınıfındaki sing( methodunu çağırdık.
        super.sing()
    }
   override fun sing(){
       println("Dog Class")
    }
}