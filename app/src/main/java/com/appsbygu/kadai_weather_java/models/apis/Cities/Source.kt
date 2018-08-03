package com.appsbygu.kadai_weather_java.models.apis.Cities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

import io.realm.RealmList
import io.realm.RealmObject

@Root(strict = false)
open class Source : RealmObject() {

    @field:Attribute(name = "title", required = false)
    var title: String? = null

    @field:Attribute(name = "link", required = false)
    var link: String? = null

    @field:ElementList(entry = "pref", inline = true, required = false)
    var prefs: RealmList<Pref>? = null

}
