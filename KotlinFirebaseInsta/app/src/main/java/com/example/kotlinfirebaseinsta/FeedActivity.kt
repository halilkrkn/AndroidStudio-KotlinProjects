package com.example.kotlinfirebaseinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore

    var userEmailFirebase : ArrayList<String> = ArrayList()
    var userCommentFirebase : ArrayList<String> = ArrayList()
    var userImageFirebase : ArrayList<String> = ArrayList()

    var adapter : FeedRecyclerAdapter? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)

        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_post){
            //Upload activity
            val intent = Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)

        }
        if (item.itemId == R.id.log_out){
            //Log out
            auth.signOut()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        getDataFromFireStore()

        //recycler view
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = FeedRecyclerAdapter(userEmailFirebase,userCommentFirebase,userImageFirebase)
        recyclerView.adapter = adapter

    }
    fun getDataFromFireStore(){

        database.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }else {
                if (snapshot != null){
                    if (!snapshot.isEmpty){

                        userImageFirebase.clear()
                        userCommentFirebase.clear()
                        userImageFirebase.clear()


                        val documents = snapshot.documents
                        for (document in documents){
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            val timestamp = document.get("date") as Timestamp
                            val date = timestamp.toDate()

                            userEmailFirebase.add(userEmail)
                            userCommentFirebase.add(comment)
                            userImageFirebase.add(downloadUrl)

                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }


        }

    }


}