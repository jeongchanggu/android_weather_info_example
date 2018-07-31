
package com.appsbygu.kadai_weather_java.models.apis.Weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Description extends RealmObject {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("publicTime")
    @Expose
    private String publicTime;

    public String getText() {
        return text;
    }

    public String getPublicTime() {
        return publicTime;
    }


}
