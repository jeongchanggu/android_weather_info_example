package com.appsbygu.kadai_weather_java.models.apis.Cities

import com.google.gson.annotations.SerializedName

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

import io.realm.RealmObject

@Root(strict = false)
open class Rss : RealmObject() {

    @SerializedName("channel")
    @field:Element(name = "channel")
    var channel: Channel? = null

    @field:Attribute(name = "version", required = false)
    var version: String? = null

//    @Element(required = false)
//    @JvmField
    @field:Attribute(name = "savedTimeStamp", required = false)
    var savedTimeStamp: Long = 0

    fun isAvailable(): Boolean {
        val timeStamp = System.currentTimeMillis()
        return savedTimeStamp + 60 * 1000 > timeStamp
    }
}
