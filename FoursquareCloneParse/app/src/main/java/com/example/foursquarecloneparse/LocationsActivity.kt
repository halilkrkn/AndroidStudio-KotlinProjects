package com.example.foursquarecloneparse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_locations.*

class LocationsActivity : AppCompatActivity() {
    var nameArray = ArrayList<String>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_place,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if (item.itemId == R.id.add_place){
           val intent = Intent(applicationContext,PlaceNameActivity::class.java)
           startActivity(intent)
       }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        getParseData()
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext,DetailActivity::class.java)
            intent.putExtra("name",nameArray[i])
            startActivity(intent)
            Toast.makeText(applicationContext, "welcome Detail", Toast.LENGTH_SHORT).show()
        }

    }
    //Kaydetttiğmiz verileri LocationActivity de listview ında gösterecez.
    fun getParseData(){

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,nameArray)
        listView.adapter = arrayAdapter

        val query = ParseQuery<ParseObject>("Locations")
        query.findInBackground { objects, e ->
            if (e != null){
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
            } else {
                if (objects.isNotEmpty()){
                    nameArray.clear()
                    for (parseObject in objects){
                        val name = parseObject.get("name") as String
                        nameArray.add(name)
                    }
                    arrayAdapter.notifyDataSetChanged()
                }
            }
        }


    }
}