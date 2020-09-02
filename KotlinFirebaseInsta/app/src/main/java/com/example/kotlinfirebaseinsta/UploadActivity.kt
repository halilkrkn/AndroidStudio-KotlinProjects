package com.example.kotlinfirebaseinsta

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_upload.*
import java.sql.Timestamp
import java.util.*
import java.util.jar.Manifest

class UploadActivity : AppCompatActivity() {

    var selectedPictureUri : Uri? = null
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
    }
    fun imageViewClicked(view: View){

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

      if (requestCode == 1 ){
          if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
              val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
              startActivityForResult(intent,2)
          }
      }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, dataUri: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && dataUri != null ){

            selectedPictureUri = dataUri.data
            try {
                if (selectedPictureUri != null){

                    if (Build.VERSION.SDK_INT >=28){

                        val source = ImageDecoder.createSource(contentResolver,selectedPictureUri!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        uploadImageView.setImageBitmap(bitmap)

                    }else{
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPictureUri)
                        uploadImageView.setImageBitmap(bitmap)
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, dataUri)
    }

    fun uploadClicked(view: View){

        //UUID : image name

        val uuıd = UUID.randomUUID()
        val imageName = "$uuıd.jpg"

        val storage = FirebaseStorage.getInstance()
        val reference = storage.reference
        val imagesReference = reference.child("images").child(imageName)

        if (selectedPictureUri != null){
            imagesReference.putFile(selectedPictureUri!!).addOnSuccessListener { taskSnapshot ->

                //Database - Firestore Kaydedilecek
                val uploadedPictureRefence = FirebaseStorage.getInstance()
                    .reference.child("images")
                    .child(imageName)

                uploadedPictureRefence.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()

                    val postMap = hashMapOf<String,Any>()
                    postMap.put("downloadUrl", downloadUrl)
                    postMap.put("userEmail", auth.currentUser!!.email.toString())
                    postMap.put("comment", uploadCommentText.text.toString())
                    postMap.put("date",com.google.firebase.Timestamp.now())

                    database.collection("Posts").add(postMap).addOnCompleteListener { task ->
                        if (task.isComplete && task.isSuccessful){
                            //Back
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(applicationContext, exception.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }


    }
}