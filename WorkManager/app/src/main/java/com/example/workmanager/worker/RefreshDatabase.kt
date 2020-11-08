package com.example.workmanager.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDatabase(val context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {

    // Work Manager içerisinde ne yapılacaksa bu doWork() sınıfı içerisnde yapmamız gerekiyor.
    override fun doWork(): Result {
        //inputData bana verilen veri al anlamındadır.
        val getData = inputData
        val myNumber = getData.getInt("intKey",0)
        refreshDatabase(myNumber)
        return Result.success()

    }

    @SuppressLint("CommitPrefEdits")
    private fun refreshDatabase(myNumber: Int) {
        
//        uygulamanın hafızasına veri eklemek için sharedPreferences kullandık.
        val sharedPreferences =  context.getSharedPreferences("package com.example.workmanager.worker",Context.MODE_PRIVATE)
        var mySavedNumber = sharedPreferences.getInt("myNumber", 0)
        mySavedNumber += myNumber
        println(mySavedNumber)
        sharedPreferences.edit().putInt("myNumber", mySavedNumber).apply()


    }

}