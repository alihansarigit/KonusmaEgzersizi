package com.yuhdeveloper.konusmaegzersizi.Adapterss

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuhdeveloper.konusmaegzersizi.Activitys.StatisticDetailsActivity
import com.yuhdeveloper.konusmaegzersizi.Point
import com.yuhdeveloper.konusmaegzersizi.R
import com.yuhdeveloper.konusmaegzersizi.User

class Rec_Adapter_Statistic(lstUsers: ArrayList<User>,activity:Activity) :
    RecyclerView.Adapter<Rec_Adapter_Statistic.StatisticViewHolder>() {


    var users = lstUsers
    var act=activity
    override fun getItemViewType(position: Int): Int {
        var s = users[position].matchType
        if (s.equals("Maraton")) {
            return 0
        } else {
            return 1
        }
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): StatisticViewHolder {

        lateinit var v: View

        if (p1 == 0) {
            v = LayoutInflater.from(p0.context).inflate(R.layout.custom_rec_statistic, p0, false)
        } else {
            v = LayoutInflater.from(p0.context).inflate(R.layout.custom_rec_statistic_duel, p0, false)
        }
        return StatisticViewHolder(v,act)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: StatisticViewHolder, p1: Int) {
        var user = users[p1]
        if (getItemViewType(p1) == 1) {
            holder.duel(user)
        } else {
            holder.maraton(user)
        }
    }


    class StatisticViewHolder(itemView: View,act:Activity) : RecyclerView.ViewHolder(itemView) {


        init {
            itemView.setOnClickListener {
//                Toast.makeText(itemView.context,match_id.toString() ,Toast.LENGTH_SHORT).show()
                var intent = Intent(itemView.context,StatisticDetailsActivity::class.java)
                intent.putExtra("id",match_id)

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    itemView.context.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(act).toBundle())
                }else{
                    itemView.context.startActivity(intent)
                }
            }
        }

        var match_id: Int=0
        var txt_winner = itemView.findViewById<TextView>(R.id.txt_customRecTeam)
        var txt_date = itemView.findViewById<TextView>(R.id.txt_customRecDate)
        var txt_correct = itemView.findViewById<TextView>(R.id.txt_customRecCorrect)
        var txt_pass = itemView.findViewById<TextView>(R.id.txt_customRecPass)
        var txt_point = itemView.findViewById<TextView>(R.id.txt_customRecPoint)


        fun duel(user: User) {
            match_id=user.matchid
            txt_winner.setText(user.winner)
            txt_date.setText(user.date)
            var whoWin: Int
            if (user.winner.equals("BERABERE")) {
                whoWin = R.drawable.circle_team_nolose
            } else if (user.winner.equals("TEAM A")) {
                whoWin = R.drawable.circle_team_a
            } else {
                whoWin = R.drawable.circle_team_b
            }
            txt_winner.setBackgroundResource(whoWin)
        }

        fun maraton(user: User) {
            match_id=user.matchid
            txt_date.setText(user.date)
            txt_correct.setText(user.p1_correctCounter.toString())
            txt_pass.setText(user.p1_passCounter.toString())
            txt_point.setText(Point.gainedPoint(user.p1_correctCounter).toString())
        }


    }

}