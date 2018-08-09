package com.appsbygu.kadai_weather_java.models.apis.cities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

import io.realm.RealmObject

@Root(strict = false)
open class City : RealmObject() {

    @field:Attribute(name = "id", required = false)
    var id: String? = null

    @field:Attribute(name = "title", required = false)
    var title: String? = null
}