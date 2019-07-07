package com.yuhdeveloper.konusmaegzersizi

data class User(private val _matchType:String,
                private val _p1_passCounter:Int,
                private val _p1_correctCounter:Int,
                private val _p2_passCounter:Int,
                private  val _p2_correctCounter:Int,
                private  val _correctWords:List<String>?,
                private  val _passWords:List<String>?,
                private val _date:String,
                private  val _time:String,
                private val _winner:String){

    val matchType:String = _matchType
    var matchid:Int=0
    val p1_passCounter:Int = _p1_passCounter
    val p1_correctCounter:Int = _p1_correctCounter

    var p1_totalScore = p1_correctCounter*5
    val p2_passCounter:Int = _p2_passCounter
    val p2_correctCounter:Int = _p2_correctCounter
    val correctWords:List<String>? = _correctWords
    val passWords:List<String>? = _passWords
    val date:String= _date
    val time:String = _time
    var winner:String=_winner

}