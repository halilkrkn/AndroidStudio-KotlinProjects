package com.example.artbooksqlite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest

class MainActivity2 : AppCompatActivity() {

    var selectedImage : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val intent = intent
        val info = intent.getStringExtra("info")
        if (info.equals("new")){
            val background = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.ic_launcher_background)
            imageView.setImageBitmap(background)
            button.visibility = View.VISIBLE
            editTextTextPersonName.setText("")
        }else{
            val name = intent.getStringExtra("name")
            editTextTextPersonName.setText(name)

            val chosen = Globals.Chosen
            val bitmap = chosen.returnImage()
            imageView.setImageBitmap(bitmap)
            button.visibility = View.INVISIBLE

        }

    }
    fun selectImage(View: View) {

        //Kullanıcı İzni Kontrol ediliyor.
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //İzin yoksa izin istedik.
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 2)
        } else {
            //İzin varsa galeriye direkt giriş yapılmasını sağladık. Yani izin Zaten Varsa
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    //Eğer izin verilirse ne yapılacağını gösterecez.
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

    //Kullanıcı izni varsa fotoğraf albümünden fotoğraf seçerse ne olacağı
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){

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

    fun save(view: View){

        //Kullanıcı tarafından seçilecek isim
        val artName = editTextTextPersonName.text.toString()
        val outputStream = ByteArrayOutputStream()
        selectedImage?.compress(Bitmap.CompressFormat.PNG,50,outputStream)
        //image yi bir veri dizisine çevirdik.
        val byteArrayImage = outputStream.toByteArray()

        //şimdi Veritabanına yukarda oluştuduğumuz veri dizisini kaydedecez.
        try {
            //Veri tabanını oluşturup table ları tanımladık
            val database = this.openOrCreateDatabase("Arts",Context.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS arts (name VARCHAR, image BLOB)")

           //Veri tabanına kullanıcıdan aldığımız verileri ekledik.
            val sqlString =  "INSERT INTO arts (name,image) VALUES (?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,artName)
            statement.bindBlob(2,byteArrayImage)
            statement.execute()



        }catch (e: Exception){
            e.printStackTrace()
        }


        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)




    }
}