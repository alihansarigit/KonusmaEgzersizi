package com.yuhdeveloper.konusmaegzersizi

class Point(){

    companion object {

        lateinit var words: ArrayList<String>

        fun removePoint():Int{
            return 250
        }

        fun startReward():Int{
            return 1000
        }

        fun gainedPoint(point:Int):Int{
            val r = (20..24).random()
            return point*r
        }

//        fun rewardPoint():Int{
//            return 1000
//        }

//        fun getWords(){
//            words.add("KELİME1")
//        }

        fun time():Long{
            return 60000
        }
    }
}