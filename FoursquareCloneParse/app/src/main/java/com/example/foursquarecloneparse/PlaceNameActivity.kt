package com.example.foursquarecloneparse

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import kotlinx.android.synthetic.main.activity_place_name.*
import java.lang.Exception
import java.util.jar.Manifest


var globalName  = ""
var globalType = ""
var globalAtmosphare = ""
var globalImage : Bitmap? = null

class PlaceNameActivity : AppCompatActivity() {

    var selectedImage : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_name)
    }

    fun selectImage(view: View){
        //GALERİYE GİRMEK İÇİN İZİN İSTİYORUZ.
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),2)
        }else {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 2){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1 &&  resultCode == Activity.RESULT_OK && data != null){
            val image = data.data

            try {
                //SDK 28 ve sonrası için bu kod bloğu kullanılıyor. ve ImageDecoder kullanılarak yapılıyor.
                if(Build.VERSION.SDK_INT >=28){
                    val source : ImageDecoder.Source = ImageDecoder.createSource(contentResolver,image!!)
                    selectedImage = ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(selectedImage)
                }else {
                    //SDK 28 öncesi için bu kod bloğu kullanılıyor.
                    selectedImage = MediaStore.Images.Media.getBitmap(this.contentResolver, image)
                    imageView.setImageBitmap(selectedImage)
                }

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun next(view: View){

        globalImage = selectedImage
        globalName = NameText.text.toString()
        globalType = typeText.text.toString()
        globalAtmosphare = atmosphareText.text.toString()

        val intent = Intent(applicationContext,MapsActivity::class.java)
        startActivity(intent)

    }
}