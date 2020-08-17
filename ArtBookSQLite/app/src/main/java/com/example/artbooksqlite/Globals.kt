package com.example.artbooksqlite

import android.graphics.Bitmap

class Globals {

    //İmageleri seçmek için global bir sınıf tanımladık.
    companion object Chosen{
        var chosenImage: Bitmap? = null
        fun returnImage():Bitmap{
            return chosenImage!!
        }
    }
}