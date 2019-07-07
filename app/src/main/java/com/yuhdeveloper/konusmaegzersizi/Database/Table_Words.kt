package com.yuhdeveloper.konusmaegzersizi.Database

class Table_Words(){
    companion object {
         val TABLE_NAME="Words"
         val COL_WordsID = "COL_WordsID"

         val COL_Word = "COL_Word"
         val COL_isCorrect = "COL_isCorrect"
         val COL_MatchID = "COL_MatchID"

        fun createMatchTable(): String = "CREATE TABLE $TABLE_NAME (" +
                "$COL_WordsID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_Word  TEXT," +
                "$COL_isCorrect  INTEGER," +
                "$COL_MatchID  INTEGER," +
                "FOREIGN KEY($COL_MatchID) REFERENCES ${Table_Match.TABLE_NAME}(${Table_Match.COL_MatchID}));"

        fun joinTable():
                String ="SELECT " +
                "  Students.StudentName," +
                "  Departments.DepartmentName" +
                " FROM Students" +
                " INNER JOIN ${Table_Words.TABLE_NAME} ON ${Table_Match.TABLE_NAME}.${Table_Match.COL_MatchID} = ${Table_Words.TABLE_NAME}.${Table_Words.COL_MatchID};"

    }

}