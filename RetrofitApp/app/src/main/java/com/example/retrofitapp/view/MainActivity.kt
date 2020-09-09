package com.example.retrofitapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapp.R
import com.example.retrofitapp.adapter.RecyclerViewAdapter
import com.example.retrofitapp.model.RetrofitModel
import com.example.retrofitapp.service.RetrofitAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var retrofitModels : ArrayList<RetrofitModel>? = null
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

        // loadDataRetrofit()
        loadDataRxjava()

        }

 /*
    private fun loadDataRetrofit(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<RetrofitModel>>{

            //İstek de Hata varsa
            override fun onFailure(call: Call<List<RetrofitModel>>, t: Throwable) {

                t.printStackTrace()
            }

            //İsteğimize Cevap Geldiyse
            override fun onResponse(
                call: Call<List<RetrofitModel>>,
                response: Response<List<RetrofitModel>>
            ) {
                if (response.isSuccessful){

                    //burdaki ?.let burda eğer response.body() boş gelmediyse kod bloğu içindekileri yap
                    response.body()?.let {

                        retrofitModels = ArrayList(it)
                        retrofitModels?.let {
                            recyclerViewAdapter =  RecyclerViewAdapter(retrofitModels!!, this@MainActivity)
                                recyclerView.adapter = recyclerViewAdapter
                        }
                            /*
                            for(retrofitModel : RetrofitModel in retrofitModels!!){
                                println(retrofitModel.currency)
                                println(retrofitModel.price)

                        }

                         */
                    }
                }
            }

        })
    }

  */
    private fun loadDataRxjava(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(RetrofitAPI::class.java)

     compositeDisposable?.add(retrofit.getData()
         .subscribeOn(Schedulers.io()) // gelen veriyi arka planda dinliyor
         .observeOn(AndroidSchedulers.mainThread()) // Main thread de işliyor
         .subscribe(this::handleResponse)) // handleResponse tarafına aktarıyor

    }

    private fun handleResponse(retrofitList: List<RetrofitModel>){
        retrofitModels = ArrayList(retrofitList)
        retrofitModels?.let {
            recyclerViewAdapter =  RecyclerViewAdapter(retrofitModels!!, this@MainActivity)
            recyclerView.adapter = recyclerViewAdapter
        }
    }
    override fun onItemClick(retrofitModel: RetrofitModel) {
        Toast.makeText(applicationContext, "Clicked : ${retrofitModel.currency}", Toast.LENGTH_SHORT).show()


    }
}