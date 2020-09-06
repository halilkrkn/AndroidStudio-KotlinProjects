package com.example.foursquarecloneparse

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import java.io.ByteArrayOutputStream

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var locationManager : LocationManager? = null
    var locationListener : LocationListener? = null
    var latitude = ""
    var longitude = ""

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.save_place,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_place){
            saveToParse()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(myListener)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener{
            //Kullanıcın yeri değiştiğinde ve yerini görmesi
            override fun onLocationChanged(p0: Location?) {
               if(p0 != null){
                    var userLocation = LatLng(p0.latitude,p0.longitude)
                    mMap.addMarker(MarkerOptions().position(userLocation).title("Your Location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,17f))
                }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {

            }

        }
        // Haritalara girmek ve işlemler yapmak için izin alıyoruz
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,2f,locationListener)
            mMap.clear()
            //Kullanıcın en son bulunduğu yer
            var lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            var lastUserLocation = LatLng(lastLocation.latitude,lastLocation.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 17f))
        }
    }


   val myListener = object  : GoogleMap.OnMapLongClickListener{
       override fun onMapLongClick(p0: LatLng?) {

           //Haritaya Uzun Tıklanınca
           mMap.clear()
           mMap.addMarker(MarkerOptions().position(p0!!).title(globalName)) // olduğumuz yerin ismini aldık.
           latitude = p0.latitude.toString() // Enlem aldık
           longitude = p0.longitude.toString() // Boylam aldık
           Toast.makeText(applicationContext, "Now Save This Place", Toast.LENGTH_SHORT).show()
       }

   }

    // Kullanıcıya izin yoksa ve o izin verildiyse bu durumu kullanıyoruz.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.size > 0){
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,2f,locationListener)

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // Locations ları Sunucuya kaydetmek
    fun saveToParse(){

        //Resim dosyasını parse dosyasına çeviriyoruz.
        val byteArrayOutputStream = ByteArrayOutputStream()
        globalImage?.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        val parseFile = ParseFile(globalName,bytes)
       // val parseFile = ParseFile("images.png",bytes)



        val parseObject = ParseObject("Locations")
        parseObject.put("name", globalName)
        parseObject.put("type", globalType)
        parseObject.put("atmosphare", globalAtmosphare)
        parseObject.put("latitude", latitude)
        parseObject.put("longitude",longitude)
        val parseUser = ParseUser.getCurrentUser().username.toString()
        parseObject.put("username",parseUser)
        parseObject.put("image",parseFile)
        parseObject.saveInBackground { e ->
            if (e != null){
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(applicationContext, "Locations Created", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,LocationsActivity::class.java)
                startActivity(intent)
            }
        }

    }
}