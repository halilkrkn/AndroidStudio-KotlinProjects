package com.example.locationsbook

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception
import java.security.Permission
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var locationManager : LocationManager? = null
    var locationListener:LocationListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps2)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(myListener)
        //locationManager initialize
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

       //locationListener initialize
        locationListener = object : LocationListener {


            override fun onLocationChanged(p0: Location?) {

                // Kullaınıcın yeri değiştiğinde yeni yerini kullanıcıya gösterebileceğimiz bir setup yaptık.
                if (p0 != null){

                    var userLocation = LatLng(p0!!.latitude,p0!!.longitude)
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

        //Haritaları kullanmak için izin alma methodlarını yazdık.
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Eğer izin yoksa ve kullanıcıdan izin istenildiğinde
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        }else {
            //İzin varsa ve yeni izin verildiğinde
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,2f,locationListener)

            //İntent Kontrol ediliyor
            val intent = getIntent()
            val info = intent.getStringExtra("info")

            if(info.equals("new")){
                // Kullanıcı Yeni yer seçtiyse ve neredeyse onu gösterecez
                // Yani Son Konumu buluyoruz.
                mMap.clear()
                val lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val lastUserManager = LatLng(lastLocation.latitude, lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserManager,17f))

            }else{
                //kullanıcı nereye tıkladıysa orası yazılacak.
                // Eski son konumu buluyoruz
                val latitude = intent.getDoubleExtra("latitude",0.0)
                val longitude = intent.getDoubleExtra("longitude",0.0)
                val name = intent.getStringExtra("name")
                val location = LatLng(latitude,longitude)
                mMap.addMarker(MarkerOptions().position(location).title(name))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,17f))
            }
        }
    }


    
    val myListener = object : GoogleMap.OnMapLongClickListener {
        override fun onMapLongClick(p0: LatLng?) {

            //Adres Bulma
            val geocoder = Geocoder(applicationContext, Locale.getDefault())

            var address = ""
            try {

                val addressList = geocoder.getFromLocation(p0!!.latitude,p0!!.longitude,1)
                if(addressList != null && addressList.size > 0){
                    if (addressList[0].thoroughfare != null){
                        address += addressList[0].thoroughfare
                        if(addressList[0].subThoroughfare != null){
                            address += addressList[0].subThoroughfare
                        }
                    }
                }else{
                    address = "New Place"
                }

            }catch (e:Exception){
                e.printStackTrace()
            }
            mMap.addMarker(MarkerOptions().position(p0!!).title(address))
            namesArray.add(address)
            locationArray.add(p0)
            Toast.makeText(applicationContext, "New Place Created", Toast.LENGTH_SHORT).show()

            try {
                //Enlem ve boylamları oluşturup Stringe dönüştürdük kaydettik.
                val latitude = p0.latitude.toString()
                val longitude = p0.longitude.toString()

                //Veritabanı işlemleri
                val database = openOrCreateDatabase("Places", Context.MODE_PRIVATE, null)
                database.execSQL("CREATE TABLE IF NOT EXISTS places (name VARCHAR, latitude VARCHAR, longitude VARCHAR)")
                val toCompile = "INSERT INTO places (names,latitude,longitude) VALUES (?,?,?)"
                val sqLiteStatement = database.compileStatement(toCompile)
                sqLiteStatement.bindString(1,address)
                sqLiteStatement.bindString(2,latitude)
                sqLiteStatement.bindString(3,longitude)

                sqLiteStatement.execute()

            }catch (e: Exception){
                e.printStackTrace()
            }



        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //İzin yoktu ve kullanıcıda izni verdiğinde
        if(grantResults.size >0){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,2f,locationListener)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}