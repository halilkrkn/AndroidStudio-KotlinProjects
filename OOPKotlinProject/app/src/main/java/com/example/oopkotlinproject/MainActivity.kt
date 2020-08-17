package com.example.oopkotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Constructor
        println("****Constructor & init ******")
        var myUser = User("Ashely",12)
       // myUser.name = "halil"
       // myUser.age = 24
        val age = myUser.age.toString()
        val name = myUser.name
        println(age)
        println(name)


        //Encapsulation
        println("******Encapsulation*******")
        var james = Musician("James","Guitar",25)
        var jamesName = james.name
        println(jamesName)
        var jamesAge = james.age.toString()
        println(jamesAge)

        var bandName = james.returnBandBane("Halil")
        var bandName2 = james.returnBandBane("Halail")
        println(bandName)
        println(bandName2)

        //Inheritance = Kalıtım miras Alma
        println("****Inheritance*****")

        var lars = SuperMusician("Lars", "Drums", 38)
        println(lars.name)
        println(lars.returnBandBane("Halil"))
        lars.sing()

        //Polymorphism
        // Aynı ismi kullanarak farklı işlemleri yapabilme özelliğidir.
        println("****Polymorphism*****")

        //Static Polymorphism
        println("*****Static Polymorphism*****")
        //Bir sınıfta birden fazla aynı isimli methodla yapılırsa Static Polymorphism olur.
        var mathematics = Mathematics()
        println(mathematics.sum())
        println(mathematics.sum(3,6))
        println(mathematics.sum(4,5,8))

        //Dynamic Polymorphism
        println("*****Dynamic Polymorphism*****")
        // Farklı Sınıflarla birden fazla aynı isimli methodlarla yapılırsa Dynamic Polymorphism olur.

        var animal = Animal()
        animal.sing()

        val karabas = Dog()
        karabas.sing()
        karabas.test()



    }
}