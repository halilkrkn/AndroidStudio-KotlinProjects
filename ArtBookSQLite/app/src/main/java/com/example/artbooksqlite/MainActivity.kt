package com.example.artbooksqlite

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val artNameArray = ArrayList<String>()
        val artImageArray = ArrayList<Bitmap>()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,artNameArray)
        listView.adapter = arrayAdapter

        try {
            //seçeceğimiz verilerin database ini tekrardan oluşturduk
            val database = this.openOrCreateDatabase("Arts", Context.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS arts (name VARCHAR, image BLOB)")

            //verileri cursor ile seçicez
            val cursor = database.rawQuery("SELECT * FROM arts", null)

            val nameIndex = cursor.getColumnIndex("name")
            val imageIndex = cursor.getColumnIndex("image")

            cursor.moveToFirst()
            while (cursor != null){
                //isimleri ekledik ve çekicez ve listview da gösterecez
                artNameArray.add(cursor.getString(nameIndex))
                // imageleri ekleyip çekicez ve listview da gösterecez
                val byteArray = cursor.getBlob(imageIndex)
                val image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                artImageArray.add(image)
                cursor.moveToNext()

                arrayAdapter.notifyDataSetChanged()
            }

            cursor?.close()
        }catch (e: Exception){

            e.printStackTrace()
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->

            val intent = Intent(applicationContext,MainActivity2::class.java)
            intent.putExtra("name", artNameArray[i])
            intent.putExtra("info","old")

            val chosen = Globals.Chosen
            chosen.chosenImage = artImageArray[i]

            startActivity(intent)

        }
    }

    //Menuyü tanımladık
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_art,menu)

        return super.onCreateOptionsMenu(menu)
    }

    //Menudekiler seçildiğinde ne olacağı tnaımladık.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //Menuye tıklanınca başka bir aktiviteye yönlendirilme yapıldı.
        if (item.itemId == R.id.add_art){
            val intent = Intent(applicationContext,MainActivity2::class.java)
            intent.putExtra("info", "new")
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }


}