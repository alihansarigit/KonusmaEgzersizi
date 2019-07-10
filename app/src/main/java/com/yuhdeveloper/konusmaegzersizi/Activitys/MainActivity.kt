package com.yuhdeveloper.konusmaegzersizi.Activitys

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.gson.GsonBuilder
import com.onesignal.OneSignal
import com.yuhdeveloper.konusmaegzersizi.Database.SqliteAdapter
import com.yuhdeveloper.konusmaegzersizi.Point
import com.yuhdeveloper.konusmaegzersizi.R
import com.yuhdeveloper.konusmaegzersizi.Receiver.ConnectivityReceiver
import com.yuhdeveloper.konusmaegzersizi.viewPagerSample.ViewPagerSampleFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity(), RewardedVideoAdListener, ConnectivityReceiver.NetworkStateReceiverListener {


    var isNetworkState:Boolean=false
    override fun onNetworkAvailable() {
        var url="http://apipool.site/Words/index.php"
        val queue = Volley.newRequestQueue(this@MainActivity)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                isNetworkState=true

                val gsonBuilder = GsonBuilder()
                val gson = gsonBuilder.create()
                var chapters = gson.fromJson<Chapters>(response, Chapters::class.java)

                Point.words=chapters.words
            },
            Response.ErrorListener {
//                Point.getWords()
                isNetworkState=false

            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

//        Toast.makeText(this@MainActivity, "Var", Toast.LENGTH_SHORT).show()

    }



    override fun onNetworkUnavailable() {
//      Point.getWords()
        isNetworkState=false

    }


    fun receiverSettings() {
        val conn = ConnectivityReceiver(this)
        conn.addListener(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(Context.CONNECTIVITY_SERVICE)
        this.registerReceiver(conn, intentFilter)
    }


    fun oneSignal() {
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
    }
    override fun onRewarded(reward: RewardItem) {
        Toast.makeText(this, "onRewarded! currency: ${reward.type} amount: ${reward.amount}",
            Toast.LENGTH_SHORT).show()
        // Reward the user.
    }

    override fun onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdClosed() {
        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
        loadAd()
        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdLoaded() {
        toolbar.getMenu().getItem(0).setVisible(true)
        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdOpened() {
        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoStarted() {
        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoCompleted() {
        Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show()
    }

    lateinit var toolbar: Toolbar
    lateinit var my_score: TextView
    lateinit var database: SqliteAdapter
    private lateinit var mRewardedVideoAd: RewardedVideoAd


    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onPause() {
        mRewardedVideoAd.destroy(this)
        super.onPause()
    }

    private fun checkPoint(java: Class<*>) {
        if(!isNetworkState){
            Toast.makeText(this@MainActivity, "İnternet Bağlantınızı Kontrol Ediniz...", Toast.LENGTH_SHORT)
                .show()
            return
        }
//        if ((database.getPoint() - Point.removePoint()) < 0) {
//            if (mRewardedVideoAd.isLoaded) {
//                mRewardedVideoAd.show()
//            }
//
//            else {
//                Toast.makeText(this@MainActivity, "Ödül Birazdan Gelecek, Lütfen Bekleyiniz.", Toast.LENGTH_SHORT)
//                    .show()
//            }
//            return
//        }

        val intent = Intent(this, java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            startActivity(intent);
        }

//        database.removePoint()
//        my_score.setText(database.getPoint().toString())
    }

    private fun loadRewardedVideoAd() {
        MobileAds.initialize(
            this,
            "ca-app-pub-2516048937640163~9527859678"
        )

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this

        loadAd()

    }

    fun loadAd() {
//        val deviceID = "D9CFDA847E26B7E04FD0A9925D2CD836"
//
//        mRewardedVideoAd.loadAd(
//            getString(R.string.reward_test3),
//            AdRequest.Builder().build()
//        )
    }


    fun pullWords() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiverSettings()
        oneSignal()

        pullWords()

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "MENU"
        toolbar.navigationIcon = null
        toolbar.inflateMenu(R.menu.menu_main2)
        toolbar.menu.getItem(0).setVisible(false)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_ads -> mRewardedVideoAd.show()
            }
            toolbar.menu.getItem(0).setVisible(false)

            true
        }

        my_score = findViewById(R.id.txt_score);
        database = SqliteAdapter(this)
        my_score.setText(database.getPoint().toString())

        lnrMaraton.setOnClickListener {
            checkPoint(MaratonActivity::class.java)
        }

        lnrvs.setOnClickListener {
            checkPoint(VersusActivity::class.java)
        }

        lnristatistik.setOnClickListener {
            var intent = Intent(this, StatisticActivity::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            } else {
                startActivity(intent)
            }
        }


        loadRewardedVideoAd()

        fab_help.setOnClickListener {

            var s = ViewPagerSampleFragment()
            s.show(supportFragmentManager,"A")
        }
    }

    override fun onResume() {
        super.onResume()
        receiverSettings()
        mRewardedVideoAd.rewardedVideoAdListener = this
//        loadAd()
        my_score.setText(database.getPoint().toString())
    }
}