package com.yuhdeveloper.konusmaegzersizi.Activitys

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.*
import com.yuhdeveloper.konusmaegzersizi.Database.SqliteAdapter
import com.yuhdeveloper.konusmaegzersizi.Point
import com.yuhdeveloper.konusmaegzersizi.R
import com.yuhdeveloper.konusmaegzersizi.User
import kotlinx.android.synthetic.main.activity_maraton.*
import java.text.SimpleDateFormat
import java.util.*



class MaratonActivity : AppCompatActivity() {


    var listWords = ArrayList<String>()


    var correctCounter:Int = 0
    var passCounter:Int = 0
    var wordsCounter:Int = 0
    var timeStart:Long = Point.time()


    var passWordsList=ArrayList<String>()
    var correctWordsList=ArrayList<String>()


    var counter1:CountDownTimer?=null
    var adView:AdView?=null
    fun loadBanner(){
        val deviceID="D9CFDA847E26B7E04FD0A9925D2CD836"
        adView = findViewById(R.id.adView)
        adView?.adSize!= AdSize.SMART_BANNER
        adView?.adUnitId!= getString(R.string.banner_ad_unit_id_test)
        val AdRequest = AdRequest.Builder().addTestDevice(deviceID).build()
        adView?.loadAd(AdRequest)
        adView?.adListener = object:AdListener(){
            override fun onAdLoaded() {
                super.onAdLoaded()
                Toast.makeText(this@MaratonActivity,"EE",Toast.LENGTH_SHORT).show()

            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                adView?.loadAd(AdRequest)
//                Toast.makeText(this@MaratonActivity," "+p0,Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maraton)

        MobileAds.initialize(this,
            "ca-app-pub-2516048937640163~9527859678")

        var toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.title="MARATON"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        loadBanner()

        dataSet()

        var date = Date();
        val formatter = SimpleDateFormat("MMM dd yyyy")
        val answer: String = formatter.format(date)



      counter1=  object : CountDownTimer(timeStart, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txt_timer.setText((millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {
                var user: User = User(
                    "Maraton",
                    passCounter,
                    correctCounter,
                    0,0,
                    correctWordsList,
                    passWordsList,
                    answer,
                    ((timeStart - 1000) / 1000).toString(),""
                )

                println("Cikti=> Type => "+user.matchType);
                println("Cikti=> passCounter => "+user.p1_passCounter);
                println("Cikti=> correctCounter => "+user.p1_correctCounter);
                println("Cikti=> passWordsList => "+user.passWords);
                println("Cikti=> correctWordsList => "+user.correctWords);
                println("Cikti=> date => "+user.date);
                println("Cikti=> time => "+user.time);

                val sql = SqliteAdapter(this@MaratonActivity)
                sql.insertMatchData(user,"")
                sql.insertWordsData(user,sql.getLastMatchID())
                doubleBackToExitPressedOnce=true

                onBackPressed()
            }
        }.start()

        btnimg_pass.setOnClickListener {
            passWordsList.add(txtWords.text.toString())
            passCounter++
            txt_passCounter.setText(passCounter.toString())
            changeWord()
        }

        btnimg_granted.setOnClickListener {
            correctWordsList.add(txtWords.text.toString())
            correctCounter++
            txt_correctCounter.setText(correctCounter.toString())
            changeWord()
        }
        txtWords.setText(listWords.get(0))

    }

    fun dataSet(){
//        listWords.add("Karşılamak")
//        listWords.add("Bağırmak")
//        listWords.add("Pamuk")
//        listWords.add("Tahta")
//        listWords.add("Kimya")

        listWords = Point.words
    }

    fun changeWord(){
        wordsCounter++
        if(wordsCounter<listWords.size){
            txtWords.setText(listWords.get(wordsCounter))
        }
    }

    var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            if(counter1!=null)
                counter1!!.cancel()
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Çıkmak için tekrar basınız...", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)


    }

    override fun onPause() {
        super.onPause()
        adView?.adListener = null
    }

    override fun onResume() {
        super.onResume()
        loadBanner()
    }
}
