package com.appsbygu.kadai_weather_java.models.apis.weathers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

open class Location : RealmObject() {

    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("area")
    @Expose
    var area: String? = null
    @SerializedName("prefecture")
    @Expose
    var prefecture: String? = null

}
