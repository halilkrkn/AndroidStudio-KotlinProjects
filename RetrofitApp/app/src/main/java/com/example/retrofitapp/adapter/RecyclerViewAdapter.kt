package com.example.retrofitapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapp.R
import com.example.retrofitapp.model.RetrofitModel
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerViewAdapter(private val retrofitList : ArrayList<RetrofitModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener{
        fun onItemClick(retrofitModel: RetrofitModel)
    }

    private val colors = arrayOf("#40a520","#FF7F50","#FFFF00","#40E0D0","#7B68EE","#FFC0CB","#DAA520","#6495ED")

    class RowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(retrofitModel: RetrofitModel, colors: Array<String>, position: Int, listener: Listener){

            itemView.setOnClickListener {
                listener.onItemClick(retrofitModel)
            }

            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            itemView.textCurrency.text = retrofitModel.currency
            itemView.textPrice.text = retrofitModel.price
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)

    }

    //Kaç tane row oluşturulacak
    override fun getItemCount(): Int {
            return retrofitList.count()
    }

    //Hangi item ne verisi gösterecek onun yapıldığı yer
    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.bind(retrofitList[position],colors,position,listener)
    }


}
