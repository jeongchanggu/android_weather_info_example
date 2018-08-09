package com.appsbygu.kadai_weather_java.models.apis.weathers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

open class Forecast : RealmObject() {

    @SerializedName("dateLabel")
    @Expose
    var dateLabel: String? = null
    @SerializedName("telop")
    @Expose
    var telop: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("image")
    @Expose
    var image: Image? = null

}
