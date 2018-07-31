
package com.appsbygu.kadai_weather_java.models.apis.Weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Location extends RealmObject {

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("prefecture")
    @Expose
    private String prefecture;

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getPrefecture() {
        return prefecture;
    }

}
