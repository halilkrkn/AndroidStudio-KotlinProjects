package com.example.kotlinfirebaseinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private  lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
    }
    fun logInClicked(view: View){
        val username = userEmailText.text.toString()
        val password = passwordText.text.toString()
       auth.signInWithEmailAndPassword(username,password).addOnCompleteListener{task ->

            if (task.isSuccessful){
                Toast.makeText(applicationContext, "Welcome: ${auth.currentUser!!.email.toString()}", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception ->
            if (exception != null){
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun signUpClicked(view: View){

        val username = userEmailText.text.toString()
        val password = passwordText.text.toString()
        auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener{task ->

            if (task.isSuccessful){

                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception ->

            if (exception !=null){
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        }
    }
}