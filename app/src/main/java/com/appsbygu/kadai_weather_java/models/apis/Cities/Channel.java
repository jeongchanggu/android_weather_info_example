package com.appsbygu.kadai_weather_java.models.apis.Cities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;


@Root(strict = false)
public class Channel extends RealmObject {

    @Element(required = false)
    private String author;

    @Element(required = false)
    private String title;

    @Namespace(reference="http://weather.livedoor.com/%5C/ns/rss/2.0")
    @Element(required = false)
    private Source source;

    @Element(required = false)
    private String lastBuildDate;

    public String getAuthor ()
    {
        return author;
    }

    public String getTitle ()
    {
        return title;
    }

    public Source getSource ()
    {
        return source;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

}