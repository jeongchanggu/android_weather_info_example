package com.appsbygu.kadai_weather_java.models.apis.weathers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

open class Image : RealmObject() {

    @SerializedName("width")
    @Expose
    var width: Int? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("height")
    @Expose
    var height: Int? = null

}
