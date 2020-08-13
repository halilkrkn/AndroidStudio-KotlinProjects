package com.example.kentlist

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


/*
        //Image
        val pisa = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.pisa)
        val colosseum = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.colosseum)
        val eiffel = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.eiffel)
        val londonbridge = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.londonbridge)

        var landmarkImages = ArrayList<Bitmap>()
        landmarkImages.add(pisa)
        landmarkImages.add(colosseum)
        landmarkImages.add(eiffel)
        landmarkImages.add(londonbridge)

 */

        //Image - Efficient = ımage leri id olarak tnaımlayacaz

        val pisaId = R.drawable.pisa
        val colosseumId = R.drawable.colosseum
        val eiffelId = R.drawable.eiffel
        val londonbridgeId = R.drawable.londonbridge

        var landmarkImagesIds = ArrayList<Int>()
        landmarkImagesIds.add(pisaId)
        landmarkImagesIds.add(colosseumId)
        landmarkImagesIds.add(eiffelId)
        landmarkImagesIds.add(londonbridgeId)


        //Data
        var landmarkNames = ArrayList<String>()
        landmarkNames.add("Pisa")
        landmarkNames.add("Colloseum")
        landmarkNames.add("Eiffel")
        landmarkNames.add("London Bridge")


        //Adapter: Layout & Data = Listeler ne kullancapımıza nasıl gösterceğime gösteren nesne

        // val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,landmarkNames) = DEFAULT ÖZELLİK
        val adapter = ArrayAdapter(this,R.layout.list_row,R.id.textView2,landmarkNames)

        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->

            val intent = Intent(applicationContext,MainActivity2::class.java)
            intent.putExtra("name", landmarkNames[position])
            intent.putExtra("image", landmarkImagesIds[position])
          //  val singleton = Singleton.selected
            //singleton.selectedImage = landmarkImages[position]
            startActivity(intent)


        }

    }
}