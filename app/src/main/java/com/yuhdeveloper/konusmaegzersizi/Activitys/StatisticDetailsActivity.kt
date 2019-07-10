package com.yuhdeveloper.konusmaegzersizi.Activitys

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.yuhdeveloper.konusmaegzersizi.Adapterss.ViewPager_Words
import com.yuhdeveloper.konusmaegzersizi.Database.SqliteAdapter
import com.yuhdeveloper.konusmaegzersizi.R
import com.yuhdeveloper.konusmaegzersizi.WordsFragment
import kotlinx.android.synthetic.main.activity_statistic_details.*


class StatisticDetailsActivity : AppCompatActivity() {

    var match_id = 0

    lateinit var lstFragment: ArrayList<Fragment>
    lateinit var lstTitles: ArrayList<String>
    lateinit var toolbar: Toolbar
    lateinit var details_ViewPager:ViewPager
    lateinit var details_TabLayout:TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic_details)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Detaylar"
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        details_ViewPager = findViewById(R.id.details_viewPager)
        details_TabLayout = findViewById(R.id.details_tabLayout)
        lstFragment= ArrayList<Fragment>()
        match_id = intent.getIntExtra("id", 0)

        var sql = SqliteAdapter(this)
        var lstWords = sql.getWords(match_id)

        val correct_fragment = WordsFragment()
        val pass_fragment = WordsFragment()

        correct_fragment.list = lstWords.correct
        correct_fragment.isCorrect = true

        pass_fragment.list = lstWords.pass
        pass_fragment.isCorrect = false

//        lstFragment = ArrayList<WordsFragment>()
        lstTitles = ArrayList<String>()
        lstFragment.add(correct_fragment)
        lstFragment.add(pass_fragment)
        lstTitles.add("BAŞARILI")
        lstTitles.add("BAŞARISIZ")

        val fragmentAdapter = ViewPager_Words(supportFragmentManager, lstFragment, lstTitles)


        details_ViewPager.adapter = fragmentAdapter
        details_TabLayout.setupWithViewPager(details_ViewPager)

        details_TabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        details_TabLayout.setTabTextColors(Color.parseColor(getString(R.string.tablayout_normal)), Color.parseColor(getString(
                    R.string.tablayout_selected)));



        details_TabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }
}
