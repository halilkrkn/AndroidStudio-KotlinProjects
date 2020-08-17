package com.example.oopkotlinproject

//Primary Constructor & Encapsulation
open class Musician(name: String, instrument: String, age: Int) {
// ****Encapsulation***
/*
 - Aslında propertyleri  bir sınıf içinde kapsüle etmek veya saklamaktır.
 -Public ve private keywordleri ile ve getter setter methodlarıyla istediğimiz propertylere değişkenlerin
 değerlerine ulaşıp ulaşmayacağımızı kendimiz ayarlayabiliriz.

 */
    // *-Ama Kotlinde Getter ve Setter Methodları yoktur. Otomatik olarak kendliğinden oluşturuluyor.
    //  - İstenirse Müdahale edilebilir.

    var name: String? = name
        private set
        get
    private var instrument: String? = instrument

    var age : Int? = age
        get
        private set
    private val  bandName : String = "Metallica"
    fun returnBandBane(password: String): String{
        if (password == "Halil"){
            return "Password succesfuly!! " + bandName
        }else{
            return "Wrong Password"
        }
    }
}



