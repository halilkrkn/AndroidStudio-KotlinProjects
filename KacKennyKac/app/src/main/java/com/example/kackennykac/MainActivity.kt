package com.example.kackennykac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var score = 0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler()
    var runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageArray.add(imageView)
        imageArray.add(imageView1)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add(imageView8)


        hideImages()


        //CountDown Timer
        object: CountDownTimer(15500,1000){

            override fun onFinish() { // zaman bitince ne olacağı

                timeText.text = "Time: 0"

                handler.removeCallbacks(runnable)
                for (images in imageArray){
                    images.visibility = View.INVISIBLE
                }


                //ALERT = Uyarı Mesajları
                val alert = AlertDialog.Builder(this@MainActivity)

                alert.setTitle("Game Over")
                alert.setMessage("Restart The Game")
                alert.setPositiveButton("Yes"){dialogInterface, i ->

                    //Restart

                    val intent = getIntent()
                    finish()
                    startActivity(intent)

                }
                alert.setNegativeButton("No"){dialogInterface, i ->
                    Toast.makeText(this@MainActivity, "Gamer Over", Toast.LENGTH_LONG).show()
                }
                alert.show()
            }


            override fun onTick(p0: Long) { //Sn de  ne olacağı
                timeText.text = "Time: " + p0/1000
            }
        }.start()

    }

    fun hideImages(){

        runnable = object : Runnable{ // Kenny i hareketlendirdik.
            override fun run() {
                for(images in imageArray){
                    images.visibility = View.INVISIBLE
                }
                var randomIndex = Random.nextInt(0, 10) // will give integer between 0 & 9
                imageArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(runnable,500)
            }
        }
       handler.post(runnable)

    }
    fun kacKenny(View: View){

        score += 1
        scoreText.text = "Score: " + score

    }
}