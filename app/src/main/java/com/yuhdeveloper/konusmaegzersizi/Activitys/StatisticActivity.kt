package com.yuhdeveloper.konusmaegzersizi.Activitys

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuhdeveloper.konusmaegzersizi.Adapterss.Rec_Adapter_Statistic
import com.yuhdeveloper.konusmaegzersizi.Database.SqliteAdapter
import com.yuhdeveloper.konusmaegzersizi.R

import kotlinx.android.synthetic.main.activity_statistic.*

class StatisticActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        var toolbar: Toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recylerView)
        toolbar.title="İSTATİSTİK"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        val sqlite=SqliteAdapter(this)

        var list = sqlite.getMatches()
        val adapters = Rec_Adapter_Statistic(list,this)

        recyclerView.adapter=adapters
        recyclerView.layoutManager = LinearLayoutManager(this)



    }

}
