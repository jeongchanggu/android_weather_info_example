package com.appsbygu.kadai_weather_java.models.apis.cities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

import io.realm.RealmList
import io.realm.RealmObject

@Root(strict = false)
open class Pref : RealmObject() {

    @field:Attribute(name = "title", required = false)
    var title: String? = null

    @field:ElementList(entry = "city", inline = true, required = false)
    var cities: RealmList<City>? = null

}