
package com.appsbygu.kadai_weather_java.models.apis.Weathers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Weather extends RealmObject {

    @SerializedName("pinpointLocations")
    @Expose
    private RealmList<PinpointLocation> pinpointLocations = null;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("forecasts")
    @Expose
    private RealmList<Forecast> forecasts = null;
    @SerializedName("publicTime")
    @Expose
    private String publicTime;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private Description description;
    @SerializedName("location")
    @Expose
    private Location location;

    public RealmList<PinpointLocation> getPinpointLocations() {
        return pinpointLocations;
    }

    public String getLink() {
        return link;
    }

    public RealmList<Forecast> getForecasts() {
        return forecasts;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public String getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

}
