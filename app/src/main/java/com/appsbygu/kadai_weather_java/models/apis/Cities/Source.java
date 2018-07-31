package com.appsbygu.kadai_weather_java.models.apis.Cities;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import io.realm.RealmList;
import io.realm.RealmObject;

@Root(strict = false)
public class Source extends RealmObject {

    @Attribute(name="title", required = false)
    private String title;

    @Attribute(name="link", required = false)
    private String link;

    @ElementList(entry="pref", inline = true, required = false)
    private RealmList<Pref> prefs;

    public String getTitle ()
    {
        return title;
    }
    public String getLink ()
    {
        return link;
    }
    public RealmList<Pref> getPrefs ()
    {
        return prefs;
    }

}
