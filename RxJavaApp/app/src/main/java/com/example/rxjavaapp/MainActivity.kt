package com.example.rxjavaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavaapp.adapter.RecyclerViewAdapter
import com.example.rxjavaapp.model.CryptoModel
import com.example.rxjavaapp.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private var cryptoModels: ArrayList<CryptoModel>? = null
    private val BASE_URL = "https://api.nomics.com/v1"
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    //Disposable = kullan at
    private var compositeDisposable: CompositeDisposable? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Rxjava daha komplex uygulamalarda işe yarıyor.
        //https://api.nomics.com/v1/prices?key=86807b574836f0859c6c4b6e2f1f43c0
        //Api Key : 86807b574836f0859c6c4b6e2f1f43c0

        compositeDisposable = CompositeDisposable()


        //RecyclerView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        loadDataRxjava()

    }
    private fun loadDataRxjava(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io()) // gelen veriyi arka planda dinliyor
            .observeOn(AndroidSchedulers.mainThread()) // Main thread de işliyor
            .subscribe(this::handleResponse)) // handleResponse tarafına aktarıyor

    }

    private fun handleResponse(retrofitList: List<CryptoModel>){
        cryptoModels = ArrayList(retrofitList)
        cryptoModels?.let {
            recyclerViewAdapter =  RecyclerViewAdapter(cryptoModels!!, this@MainActivity)
            recyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.currency}",Toast.LENGTH_LONG ).show()
    }
    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }
}