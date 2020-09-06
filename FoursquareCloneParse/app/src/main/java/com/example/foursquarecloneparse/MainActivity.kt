package com.example.foursquarecloneparse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.parse.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/* // PARSE TEKNİKLERİ
//burada test olarak serverımıza bağlanıyormuyuz diye test yaptık
    // *****Verileri oluşturma****
*

        val parseObj = ParseObject("Test") // dashboard da gözükecek bir snıf olışturduk test adında

        parseObj.put("test",1) // bir Anahtar kelime ve değer soruyor
        parseObj.saveEventually({e: ParseException? ->
            if (e != null){
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Successfull", Toast.LENGTH_SHORT).show()

            }
        })
       // **** Verileri Okumak ******
       // Oluşturduğumuz verilei okuyoruz çekiyoruz.

 val query = ParseQuery.getQuery<ParseObject>("Test")
        query.whereLessThan("test", 2) // burada 2 den küçük olan değerleri filtreleyebilriz.
        query.findInBackground { objects, e ->
            if (e != null){
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                if (objects.size > 0){
                    for (parseObject in objects){
                        val name = parseObject.get("test") as String
                    }
                }

            }
        }
        */
        ParseAnalytics.trackAppOpenedInBackground(intent)

    }

    //Kullanıcı Girişi ve Otomatik tekrarsız Giriş
    fun SignIn(view: View){

        ParseUser.logInInBackground(userNameText.text.toString(),userPasswordText.text.toString(),
            LogInCallback { user, e ->
                if ( e != null){
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(applicationContext, "Welcome ${user.username}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext,LocationsActivity::class.java)
                    startActivity(intent)
                }


            })

    }

    // Kullanıcı Oluşturuyoruz.
    fun SignUp (view: View){

        val user = ParseUser()
        user.username = userNameText.text.toString()
        user.setPassword(userPasswordText.text.toString())

        user.signUpInBackground { e: ParseException? ->
            if (e != null){
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "User Created", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,LocationsActivity::class.java)
                startActivity(intent)
            }
        }

    }

}