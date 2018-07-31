
package com.appsbygu.kadai_weather_java.models.apis.Weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Forecast extends RealmObject {

    @SerializedName("dateLabel")
    @Expose
    private String dateLabel;
    @SerializedName("telop")
    @Expose
    private String telop;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("image")
    @Expose
    private Image image;

    public String getDateLabel() {
        return dateLabel;
    }

    public String getTelop() {
        return telop;
    }

    public String getDate() {
        return date;
    }

    public Image getImage() {
        return image;
    }

}
