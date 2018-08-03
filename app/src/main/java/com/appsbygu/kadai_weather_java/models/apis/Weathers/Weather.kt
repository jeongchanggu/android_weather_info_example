package com.appsbygu.kadai_weather_java.models.apis.Weathers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmList
import io.realm.RealmObject

open class Weather(
        @SerializedName("pinpointLocations")
        @Expose
        var pinpointLocations: RealmList<PinpointLocation>? = null,

        @SerializedName("link")
        @Expose
        var link: String? = null,

        @SerializedName("forecasts")
        @Expose
        var forecasts: RealmList<Forecast>? = null,

        @SerializedName("publicTime")
        @Expose
        var publicTime: String? = null,

        @SerializedName("title")
        @Expose
        var title: String? = null,

        @SerializedName("description")
        @Expose
        var description: Description? = null,

        @SerializedName("location")
        @Expose
        var location: Location? = null
) : RealmObject()
