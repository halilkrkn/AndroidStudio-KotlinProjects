package com.example.oopkotlinproject

//**** Constructer & init ****
class User {

    //Property = Özellik
    var name : String? = null
    var age : Int? = null

    //**** Constructer & init ****
    //constructor = sınıfın objelerini inşa ediyor.
    //Yani bir sınıftan bir obje oluşturulduğunda çağrılan ilk fonksiyon
    constructor(nameInput: String?, ageInput: Int?) {
        this.name = nameInput
        this.age = ageInput
        println("User Created")
    }
    // *** init ****
    // İlk obje oluşturulduğund açok ayrı bir işlem yapılacaksa o zaman init kullanılır
    // Constructor ile hemen hemen aynı işlevleri görüyor.
    // Yani init de bir sınıftan bir obje oluşturulduğunda ilk çağrılan fonksiyon olacak.
    init {

        println("init")
    }


}
