package com.example.retrofitapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofitapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Rxjava daha komplex uygulamalarda işe yarıyor.

        //https://api.nomics.com/v1/prices?key=86807b574836f0859c6c4b6e2f1f43c0
        //Api Key : 86807b574836f0859c6c4b6e2f1f43c0
    }
}