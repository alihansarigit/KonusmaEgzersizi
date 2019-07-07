package com.yuhdeveloper.konusmaegzersizi.Activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.yuhdeveloper.konusmaegzersizi.Adapterss.Rec_Adapter_Statistic
import com.yuhdeveloper.konusmaegzersizi.Database.SqliteAdapter
import com.yuhdeveloper.konusmaegzersizi.R

import kotlinx.android.synthetic.main.activity_statistic.*

class StatisticActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        var toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.title="İSTATİSTİK"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        val sqlite=SqliteAdapter(this)

        var list = sqlite.getMatches()
        val adapters = Rec_Adapter_Statistic(list,this)

        recylerView.adapter=adapters
        recylerView.layoutManager = LinearLayoutManager(this)



    }

}
