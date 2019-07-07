package com.yuhdeveloper.konusmaegzersizi.Activitys

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.*
import com.yuhdeveloper.konusmaegzersizi.Database.SqliteAdapter
import com.yuhdeveloper.konusmaegzersizi.Point
import com.yuhdeveloper.konusmaegzersizi.R
import com.yuhdeveloper.konusmaegzersizi.User
import kotlinx.android.synthetic.main.activity_versus.*
import java.text.SimpleDateFormat
import java.util.*

class VersusActivity : AppCompatActivity() {

    var listWords = ArrayList<String>();

    var passWordsList = ArrayList<String>()
    var correctWordsList = ArrayList<String>()

    var p1_correctCounter = 0
    var p1_passCounter = 0

    var p2_correctCounter = 0
    var p2_passCounter = 0

    var time: Long = Point.time()
    var isP1: Boolean = true
    var turSayisi: Int = 0

    var datenow: String = ""

    var wordsCounter: Int = 0
    var winner: String = ""

    var counter1: CountDownTimer? = null
    var counter2: CountDownTimer? = null
    var adView: AdView?=null

    fun loadBanner(){
        val deviceID = "D9CFDA847E26B7E04FD0A9925D2CD836"
        adView = findViewById(R.id.adView)
        adView?.adSize!= AdSize.SMART_BANNER
        adView?.adUnitId!= getString(R.string.banner_ad_unit_id_test)
        val AdRequest = AdRequest.Builder().addTestDevice(deviceID).build()
        adView?.loadAd(AdRequest)
        adView?.adListener = object: AdListener(){
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                adView?.loadAd(AdRequest)
//                Toast.makeText(this@MaratonActivity," "+p0,Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_versus)

        MobileAds.initialize(this,
            "ca-app-pub-2516048937640163~9527859678")
        loadBanner()


        var toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.title="VERSUS"
        toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()
        })


        dataSet() // verileri aktar

        var date = Date();
        val formatter = SimpleDateFormat("MMM dd yyyy")
        datenow = formatter.format(date)


        Play()

        btnimg_granted.setOnClickListener {
            answer(true)
        }

        btnimg_pass.setOnClickListener {
            answer(false)
        }


        txtWords.setText(listWords.get(0))

    }

    fun changeWord() {
        wordsCounter++
        if (wordsCounter < listWords.size) {
            txtWords.setText(listWords.get(wordsCounter))
        }
    }

    private fun answer(isGranted: Boolean) {

        if (isGranted) {

            correctWordsList.add(txtWords.text.toString())
            if (isP1) {
                p1_correctCounter++
                txt_team_a_score.setText("TEAM A : " + p1_correctCounter + " | " + p1_passCounter)
            } else {
                p2_correctCounter++
                txt_team_b_score.setText("TEAM B : " + p2_correctCounter + " | " + p2_passCounter)
            }
        } else {
            passWordsList.add(txtWords.text.toString())
            if (isP1) {
                p1_passCounter++
                txt_team_a_score.setText("TEAM A : " + p1_correctCounter + " | " + p1_passCounter)
            } else {
                p2_passCounter++
                txt_team_b_score.setText("TEAM B : " + p2_correctCounter + " | " + p2_passCounter)
            }
        }

        changeWord()
    }


    fun dataSet() {
//        listWords.add("Karşılamak")
//        listWords.add("Bağırmak")
//        listWords.add("Pamuk")
//        listWords.add("Tahta")
//        listWords.add("Kimya")

        listWords = Point.words

    }


    fun selector() {
        if (isP1) {
            img_selector_blue.visibility = View.VISIBLE
            img_selector_red.visibility = View.INVISIBLE

            lnr_team_a.alpha = 1f
            lnr_team_b.alpha = 0.4f
        } else {
            img_selector_blue.visibility = View.INVISIBLE
            img_selector_red.visibility = View.VISIBLE

            lnr_team_a.alpha = 0.4f
            lnr_team_b.alpha = 1f
        }
    }

    fun Play() {
        if (turSayisi < 2) {
            turSayisi++
            if (isP1) {
                selector()

                counter1 = object : CountDownTimer(time, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        txt_timer.setText((millisUntilFinished / 1000).toString())

                    }

                    override fun onFinish() {
//                        var user = User(
//                            "VS",
//                            p1_passCounter,
//                            p1_correctCounter,
//                            correctWordsList,
//                            passWordsList,
//                            datenow,
//                            ((time - 1000) / 1000).toString()
//                        )

//                        if (turSayisi == 3) {
//                            listUsers.add(user)
//                        }

                        isP1 = !isP1
                        Play()
                    }
                }.start()
            } else {
                selector()

                counter2 = object : CountDownTimer(time, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        txt_timer.setText((millisUntilFinished / 1000).toString())
                    }

                    override fun onFinish() {
                        var user = User(
                            "VS",
                            p1_passCounter,
                            p2_correctCounter,
                            p2_passCounter,
                            p2_correctCounter,
                            correctWordsList,
                            passWordsList,
                            datenow,
                            ((time - 1000) / 1000).toString(),""
                        )



                        if (turSayisi == 2) {

//                            btnimg_granted.visibility= View.INVISIBLE
//                            btnimg_pass.visibility= View.INVISIBLE

                            val dialog = AlertDialog.Builder(this@VersusActivity)
                            val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
                            val img_medalA = dialogView.findViewById<ImageView>(R.id.img_team_a_medal)
                            val img_medalB = dialogView.findViewById<ImageView>(R.id.img_team_b_medal)
                            val txt_scoreA = dialogView.findViewById<TextView>(R.id.txt_team_a_score)
                            val txt_scoreB = dialogView.findViewById<TextView>(R.id.txt_team_b_score)
                            val btn_versus = dialogView.findViewById<Button>(R.id.btn_versus)
                            val btn_maraton = dialogView.findViewById<Button>(R.id.btn_maraton)
                            val btn_menu = dialogView.findViewById<Button>(R.id.btn_menu)

                            btn_versus.setOnClickListener {
                                val intent = Intent(this@VersusActivity, VersusActivity::class.java)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    startActivity(
                                        intent,
                                        ActivityOptions.makeSceneTransitionAnimation(this@VersusActivity).toBundle()
                                    )
                                } else {
                                    startActivity(intent)
                                }
                                finish()
                            }
                            btn_maraton.setOnClickListener {
                                val intent = Intent(this@VersusActivity, MaratonActivity::class.java)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    startActivity(
                                        intent,
                                        ActivityOptions.makeSceneTransitionAnimation(this@VersusActivity).toBundle()
                                    )
                                } else {
                                    startActivity(intent)
                                }
                                finish()

                            }
                            btn_menu.setOnClickListener {
                                doubleBackToExitPressedOnce=true

                                onBackPressed()
                            }

                            dialog.setView(dialogView)
                            dialog.setCancelable(false)
                            val customDialog = dialog.create()
                            customDialog.show()

                            txt_scoreA.setText((p1_correctCounter - p1_passCounter).toString())
                            txt_scoreB.setText((p2_correctCounter - p2_passCounter).toString())

                            if ((p1_correctCounter - p1_passCounter) > (p2_correctCounter - p2_passCounter)) {
                                winner = "TEAM A"
                                img_medalA.visibility = View.VISIBLE
                                img_medalB.visibility = View.INVISIBLE
                            } else if ((p1_correctCounter - p1_passCounter) < (p2_correctCounter - p2_passCounter)) {
                                winner = "TEAM B"
                                img_medalA.visibility = View.INVISIBLE
                                img_medalB.visibility = View.VISIBLE
                            } else {
                                winner = "BERABERE"
                                img_medalA.visibility = View.VISIBLE
                                img_medalB.visibility = View.VISIBLE
                            }

                           // listUsers.add(user)
                            var sql = SqliteAdapter(this@VersusActivity)
                            user.winner=winner
                            sql.insertMatchData(user, winner)
                            sql.insertWordsData(user,sql.getLastMatchID())
                            var list: ArrayList<User> = sql.getMatches()

                            for (i in list) {
                                println("Cikti=> Type => " + i.matchType);
                                println("Cikti=> passCounter => " + i.p1_passCounter);
                                println("Cikti=> correctCounter => " + i.p1_correctCounter);
                                println("Cikti=> passWordsList => " + i.passWords);
                                println("Cikti=> correctWordsList => " + i.correctWords);
                                println("Cikti=> date => " + i.date);
                                println("Cikti=> time => " + i.time);
                                println("Cikti=>>>>>>>>>>>>");
                            }


                        }
                        isP1 = !isP1

                        Play()
                    }
                }.start()
            }
        }
    }

    var doubleBackToExitPressedOnce:Boolean=false
    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            if(counter1!=null)
                counter1!!.cancel()

            if(counter2!=null)
                counter2!!.cancel()

            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Çıkmak için tekrar basınız...", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)


    }
}
