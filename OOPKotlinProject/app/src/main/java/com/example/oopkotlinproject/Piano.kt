package com.example.oopkotlinproject

//İnterface & Abstract
class Piano : HouseDecor, Instrument{
    var brand: String? = null
    var digital: Boolean? = null

    override var roomName: String
        get() = "Kitchen"
        set(value) {
        }

    override fun info() {
        println("Hello Piano")
    }
}