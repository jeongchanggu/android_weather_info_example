package com.appsbygu.kadai_weather_java.models.apis.Weathers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

open class PinpointLocation : RealmObject() {

    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}
