package com.appsbygu.kadai_weather_java.models.apis.Cities

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

import io.realm.RealmObject


@Root(strict = false)
open class Channel : RealmObject() {

    @field:Element(name= "author", required = false)
    var author: String? = null

    @field:Element(name= "title", required = false)
    var title: String? = null

    @Namespace(reference = "http://weather.livedoor.com/%5C/ns/rss/2.0")
    @field:Element(name= "source", required = false)
    var source: Source? = null

    @field:Element(name= "lastBuildDate", required = false)
    var lastBuildDate: String? = null

}