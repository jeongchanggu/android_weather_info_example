package com.appsbygu.kadai_weather_java.models.apis.Cities;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import io.realm.RealmList;
import io.realm.RealmObject;

@Root(strict = false)
public class Pref extends RealmObject {

    @Attribute(name="title", required = false)
    private String title;

    @ElementList(entry="city", inline = true, required = false)
    private RealmList<City> cities;

    public String getTitle ()
    {
        return title;
    }
    public RealmList<City> getCities ()
    {
        return cities;
    }

}