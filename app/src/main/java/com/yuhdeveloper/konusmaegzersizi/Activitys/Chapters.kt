package com.yuhdeveloper.konusmaegzersizi.Activitys

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Chapters {

    @SerializedName("words")
    @Expose
    lateinit var words:ArrayList<String>
}
