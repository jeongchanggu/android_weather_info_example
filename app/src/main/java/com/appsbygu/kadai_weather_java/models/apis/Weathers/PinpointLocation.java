
package com.appsbygu.kadai_weather_java.models.apis.Weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PinpointLocation extends RealmObject{

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

}
