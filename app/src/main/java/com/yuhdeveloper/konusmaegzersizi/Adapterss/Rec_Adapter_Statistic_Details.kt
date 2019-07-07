package com.yuhdeveloper.konusmaegzersizi.Adapterss

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.yuhdeveloper.konusmaegzersizi.R

class Rec_Adapter_Statistic_Details(Words:ArrayList<String>,_isCorrect:Boolean) :
    RecyclerView.Adapter<Rec_Adapter_Statistic_Details.DetailsViewHolder>() {

    var users = Words
    var isCorrect = _isCorrect
    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailsViewHolder {
        lateinit var v: View
        v = LayoutInflater.from(p0.context).inflate(R.layout.custom_details, p0, false)
        return DetailsViewHolder(v)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, p1: Int) {
        if(!isCorrect){
            holder.frmLayout.setBackgroundResource(R.color.pass)
        }

        holder.txtWord.setText(users[p1])
    }


    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {

        }
        var txtWord:TextView=itemView.findViewById(R.id.custom_details_txtWords)
        var frmLayout: FrameLayout =itemView.findViewById(R.id.custom_details_card)


    }

}