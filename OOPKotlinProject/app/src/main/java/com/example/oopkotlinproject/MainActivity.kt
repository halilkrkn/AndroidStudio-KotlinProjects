package com.example.oopkotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Bir snıf sadece bir Sınıftan Kalıtım alabilir.
        //Ama bir Sınıf birden fazla Sınıfla kalıtım yapabilir.

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


        //Abstract (Soyut Sınıf) & Interface
        //Abstract (Soyut Sınıf) = Obje=Instance oluşturamayacağımız bir Snıftır.
        // Bir sınıf tek bir Abstract Sınıfla kalıtım yapar.
        // var myPerson = People() - Burada People abstract Classının Objesini oluşturamadık.
        // Interface (AraYüz) = Bir Sınıf birden fazla interfacele kalıtım yapabilir.

        println("*******Abstract - Interface********")

        var myPiano = Piano()
        myPiano.info()
        myPiano.brand = "Yamaha"
        myPiano.digital = false
        println(myPiano.roomName)

        //Lambda Expressions
        println("***Lambda Expressions***")
        // Bir fonksiyonu tek bir satırda yazmak için geliştirilen bir özellik
        fun printString(myString: String){
            println(myString)
        }
        printString("My Test String")

        val testString = {testString : String -> println(testString)}
        testString("My Lamdda Test String")

        val multiplyLambda = { a:Int, b: Int -> a*b }
        println(multiplyLambda(5,4))

        val multiplyLambda2 : (Int,Int) -> Int = { a,b -> a*b }
        println(multiplyLambda2(3,4))

        //İleri Seviye Lambda Expressions
        //Asynchrnous = Senkronize olmayan bir durum
        //callback function, listener function, copmletion function
        println("****Asynchrnous****")

        fun downloadMusicians(url: String, completion: () -> Unit ){
            //url -> download
            //data
            completion()
        }
        downloadMusicians("metalica.com",{
            println("completed && ready")
        })
        fun downloadMusicians2(url: String, completion: (Musician) -> Unit ){
            //url -> download
            //data
            val kirkHammet = Musician("Kirk", "Guitar", 60)
            completion(kirkHammet)
        }
        downloadMusicians2("metalica.com",{musician ->
            println(musician.name)
        })


    }
}