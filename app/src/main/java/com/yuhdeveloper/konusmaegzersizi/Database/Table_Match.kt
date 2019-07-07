package com.yuhdeveloper.konusmaegzersizi.Database

class Table_Match(){


    companion object {
        val TABLE_NAME="Matches"
        val COL_MatchID = "COL_MatchID" //

         val COL_MatchType = "COL_MatchType"
         val COL_p1_passCounter = "COL_p1_passCounter" //
         val COL_p1_correctCounter = "COL_p1_correctCounter"
         val COL_p1_score="COL_p1_score"
         val COL_p2_passCounter = "COL_p2_passCounter" //
         val COL_p2_correctCounter = "COL_p2_correctCounter"
         val COL_date = "COL_date"
         val COL_time = "COL_time"
         val COL_winner = "COL_winner"


        fun createMatchTable(): String = "CREATE TABLE $TABLE_NAME (" +
                "$COL_MatchID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_MatchType  TEXT," +
                "$COL_p1_passCounter  TEXT," +
                "$COL_p1_score  INTEGER," +
                "$COL_p1_correctCounter  TEXT," +
                "$COL_p2_passCounter  TEXT," +
                "$COL_p2_correctCounter  TEXT," +
                "$COL_date  TEXT," +
                "$COL_time  TEXT," +
                "$COL_winner TEXT)"
    }
}