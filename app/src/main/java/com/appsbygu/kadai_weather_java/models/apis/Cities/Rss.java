package com.appsbygu.kadai_weather_java.models.apis.Cities;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;

@Root(strict = false)
public class Rss extends RealmObject {

    @SerializedName("channel")
    @Element(name="channel")
    private Channel channel;

    @Attribute(name="version", required=false)
    private String version;

    @Element(required=false)
    private long savedTimeStamp;

    public Channel getChannel ()
    {
        return channel;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getSavedTimeStamp() {
        return savedTimeStamp;
    }

    public void setSavedTimeStamp(long savedTimeStamp) {
        this.savedTimeStamp = savedTimeStamp;
    }

    public boolean isAvailable() {
        long timeStamp = System.currentTimeMillis();
        return savedTimeStamp + (60*1000) > timeStamp;
    }

}
