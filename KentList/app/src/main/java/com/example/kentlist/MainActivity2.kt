package com.example.kentlist

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        val intent = getIntent()
        val landmarkName = intent.getStringExtra("name")
        textView.text = landmarkName

        val landmarkImageId = intent.getIntExtra("image", 0)

        val selectedBitmap = BitmapFactory.decodeResource(applicationContext.resources, landmarkImageId)
        imageView.setImageBitmap(selectedBitmap)

/*
        val singleton = Singleton.selected
        val selectedBitmap = singleton.selectedImage
        imageView.setImageBitmap(selectedBitmap)

 */


    }
}