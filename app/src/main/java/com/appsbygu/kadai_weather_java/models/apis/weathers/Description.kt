package com.appsbygu.kadai_weather_java.models.apis.weathers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

open class Description : RealmObject() {

    @SerializedName("text")
    @Expose
    var text: String? = null

    @SerializedName("publicTime")
    @Expose
    var publicTime: String? = null


}
