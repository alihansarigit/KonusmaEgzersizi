package com.yuhdeveloper.konusmaegzersizi.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.yuhdeveloper.konusmaegzersizi.Point
import com.yuhdeveloper.konusmaegzersizi.User
import com.yuhdeveloper.konusmaegzersizi.Word

class SqliteAdapter(val context: Context) : SQLiteOpenHelper(context, "DatabaseName", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Table_Match.createMatchTable())
        db?.execSQL(Table_Words.createMatchTable())
        db?.execSQL(Table_User.createMatchTable())

        insertPoint(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertPoint(sqliteDB: SQLiteDatabase?){
        val contentValues = ContentValues()
        contentValues.put(Table_User.COL_Point,1000)
        sqliteDB?.insert(Table_User.TABLE_NAME,null,contentValues)
    }

    fun insertMatchData(user: User,winner:String) {
        val sqliteDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Table_Match.COL_p1_correctCounter, user.p1_correctCounter)
        contentValues.put(Table_Match.COL_p1_passCounter, user.p1_passCounter)
        contentValues.put(Table_Match.COL_p1_score, user.p1_totalScore)
        contentValues.put(Table_Match.COL_p2_correctCounter, user.p2_correctCounter)
        contentValues.put(Table_Match.COL_p2_passCounter, user.p2_passCounter)
        contentValues.put(Table_Match.COL_date, user.date)
        contentValues.put(Table_Match.COL_MatchType, user.matchType)
        contentValues.put(Table_Match.COL_winner, winner)
        contentValues.put(Table_Match.COL_time, user.time)

        val result = sqliteDB.insert(Table_Match.TABLE_NAME, null, contentValues)

     //   Toast.makeText(context, if (result != -1L) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()

        addPoint(user.p1_correctCounter)

    }

    fun getLastMatchID(): Int {
        val sqliteDB = this.readableDatabase

        var id = sqliteDB.rawQuery(
            "select ${Table_Match.COL_MatchID} from ${Table_Match.TABLE_NAME} order by ${Table_Match.COL_MatchID} desc Limit 1",
            null
        )

        var lastid: Int = -1
        if (id != null && id.moveToFirst()) {
            lastid = id.getInt(0)
        }
        return lastid
    }

    fun getMatches(): ArrayList<User> {
        var lstUser = ArrayList<User>()
        val sqliteDB = this.readableDatabase

        var cursor =
            sqliteDB.rawQuery("select * from ${Table_Match.TABLE_NAME} order by ${Table_Match.COL_MatchID} desc", null)
        while (cursor.moveToNext()) {
            var us= User(
                cursor.getString(cursor.getColumnIndex(Table_Match.COL_MatchType)),
                cursor.getInt(cursor.getColumnIndex(Table_Match.COL_p1_passCounter)),
                cursor.getInt(cursor.getColumnIndex(Table_Match.COL_p1_correctCounter)),
                cursor.getInt(cursor.getColumnIndex(Table_Match.COL_p2_passCounter)),
                cursor.getInt(cursor.getColumnIndex(Table_Match.COL_p2_correctCounter)),
                null,null,
                cursor.getString(cursor.getColumnIndex(Table_Match.COL_date)),
                "30",
                cursor.getString(cursor.getColumnIndex(Table_Match.COL_winner))
            )
            us.matchid=cursor.getInt(cursor.getColumnIndex(Table_Match.COL_MatchID))
//            us.p1_totalScore = cursor.getInt(cursor.getColumnIndex(Table_Match.COL_p1_score))

            lstUser.add(us)
        }

        return lstUser
    }

    fun getPoint(): Int {
        val sqliteDB = this.readableDatabase
        var score:Int=0
        var cursor = sqliteDB.rawQuery("select ${Table_User.COL_Point} as Total from ${Table_User.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            score = cursor.getInt(cursor.getColumnIndex("Total"))
        }

//        if(score<0){
//            resetPoint()
//            return 0
//        }else{
//            return score
//        }

        return score

        sqliteDB.close()
    }

    fun removePoint() {
        val sqliteDB = this.writableDatabase
        var contentValues:ContentValues = ContentValues()
        contentValues.put(Table_User.COL_Point,(getPoint()-Point.removePoint()))

        val result = sqliteDB.update(Table_User.TABLE_NAME,contentValues,"${Table_User.COL_UserID}=1",null)

       // Toast.makeText(context, if (result != -1) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()

        sqliteDB.close()
    }
    fun addPoint(point:Int) {
        val sqliteDB = this.writableDatabase
        var contentValues:ContentValues = ContentValues()
        contentValues.put(Table_User.COL_Point,getPoint()+Point.gainedPoint(point))

        val result = sqliteDB.update(Table_User.TABLE_NAME,contentValues,"${Table_User.COL_UserID}=1",null)

     //   Toast.makeText(context, if (result != -1) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()

        sqliteDB.close()
    }

    fun addPointRewared(point:Int) {
        val sqliteDB = this.writableDatabase
        var contentValues:ContentValues = ContentValues()
        contentValues.put(Table_User.COL_Point,getPoint()+point)

        val result = sqliteDB.update(Table_User.TABLE_NAME,contentValues,"${Table_User.COL_UserID}=1",null)

        //   Toast.makeText(context, if (result != -1) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()

        sqliteDB.close()
    }

    fun insertWordsData(user: User, match_id: Int) {
        val sqliteDB = this.writableDatabase
        val contentValues = ContentValues()
        for (i in user.correctWords!!) {
            contentValues.put(Table_Words.COL_Word, i)
            contentValues.put(Table_Words.COL_MatchID, match_id)
            contentValues.put(Table_Words.COL_isCorrect, 1)
            val result = sqliteDB.insert(Table_Words.TABLE_NAME, null, contentValues)
            println("tabbooo=> " + Table_Words.createMatchTable())
//            Toast.makeText(context, if (result != -1L) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT)
//                .show()
        }

        for (i in user.passWords!!) {
            contentValues.put(Table_Words.COL_Word, i)
            contentValues.put(Table_Words.COL_MatchID, match_id)
            contentValues.put(Table_Words.COL_isCorrect, 0)
            val result = sqliteDB.insert(Table_Words.TABLE_NAME, null, contentValues)
         //   Toast.makeText(context, if (result != -1L) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getWords(match_id: Int): Word {

        var correctList = ArrayList<String>()
        var passList = ArrayList<String>()

        val sql = this.readableDatabase

        var cursor = sql.rawQuery(
            "select * from ${Table_Words.TABLE_NAME} where ${Table_Words.COL_MatchID} = $match_id order by ${Table_Words.COL_WordsID} desc",
            null
        )
//        var cursor = sql.rawQuery("select * from ${Table_Words.TABLE_NAME}",null)

        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("${Table_Words.COL_isCorrect}")) == 0) {
                passList.add(cursor.getString(cursor.getColumnIndex("${Table_Words.COL_Word}")))
            } else {
                correctList.add(cursor.getString(cursor.getColumnIndex("${Table_Words.COL_Word}")))
            }
        }

        var words = Word(correctList, passList)
        return words
    }


    fun getLastWordsID(): Int {
        val sqliteDB = this.readableDatabase

        var id = sqliteDB.rawQuery(
            "select * from ${Table_Words.TABLE_NAME} order by ${Table_Words.COL_WordsID} desc Limit 1",
            null
        )

        var lastid: Int = -1
        if (id != null && id.moveToFirst()) {
            lastid = id.getInt(0)
        }
        return lastid
    }

}