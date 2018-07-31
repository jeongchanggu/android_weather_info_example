package com.appsbygu.kadai_weather_java.models.apis.Cities;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;

@Root(strict = false)
public class City extends RealmObject {

    @Attribute(name="id", required = false)
    private String id;

    @Attribute(name="title", required = false)
    private String title;


    public String getId ()
    {
        return id;
    }
    public String getTitle ()
    {
        return title;
    }
}