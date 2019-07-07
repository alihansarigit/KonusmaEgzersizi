package com.yuhdeveloper.konusmaegzersizi.Database

class Table_User(){


    companion object {
        val TABLE_NAME="User"
         val COL_UserID = "COL_UserID"
         val COL_Point = "COL_Point" //

        fun createMatchTable(): String = "CREATE TABLE $TABLE_NAME (" +
                "$COL_UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${COL_Point} INTEGER)"
    }
}