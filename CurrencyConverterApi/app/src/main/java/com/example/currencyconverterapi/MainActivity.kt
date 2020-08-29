package com.example.currencyconverterapi

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getRates(view: View){

        val download = Download()


        try {
            //Çekeceğimiz siteden verilerinolduğu url i ekledik.
            val url = "http://data.fixer.io/api/latest?access_key=649d558bc66fce63b0b964ec15625b9f"
            download.execute(url)
        } catch (e: Exception){
         e.printStackTrace()
        }

    }
   inner class Download: AsyncTask<String, Void, String>() {


        //Arka planda download işlemleri yapıcaz.
        override fun doInBackground(vararg p0: String?): String {

            var result = ""

            var url : URL
            var httpUrlConnection : HttpURLConnection

            try {


                url = URL(p0[0]) // sağlanan ilk url bilgisini çek demek.
                httpUrlConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpUrlConnection.inputStream
                // inputstream den gelen bağlantıyı akışı okuyacak
                val inputStreamReader = InputStreamReader(inputStream)

                var data = inputStream.read()
                while(data > 0){

                    val character = data.toChar()
                    result += character
                    data = inputStreamReader.read()
                }
                return result
            } catch (e: Exception){

                e.printStackTrace()

            }

            return result
        }

        //Tüm işlemler bittikten sonra neler olacağı ile ilgili bir fonk. yazıcaz.
        override fun onPostExecute(result: String?) {
            // println(result)
            try {

                val jsonObject = JSONObject(result)
                val base = jsonObject.getString("base")
                //  println(base)
                val rates = jsonObject.getString("rates")
                //  println(rates)

                val jsonObject1 = JSONObject(rates)
                val turkishLira = jsonObject1.getString("TRY")
                val cad = jsonObject1.getString("CAD")
                val gbp = jsonObject1.getString("GBP")
                val usd = jsonObject1.getString("USD")
                val chf = jsonObject1.getString("CHF")
                // println(turkishLira)
                tryText.text = "TRY: " + turkishLira
                cadText.text = "CAD: " + cad
                gbptext.text = "GBP: " + gbp
                usdfText.text = "USD: " + usd
                chfText.text = "USD: " + chf


            } catch (e: Exception) {
                e.printStackTrace()
            }
            super.onPostExecute(result)
        }

    }


}