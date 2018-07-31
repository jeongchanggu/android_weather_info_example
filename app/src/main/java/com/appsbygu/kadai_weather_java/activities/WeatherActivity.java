package com.appsbygu.kadai_weather_java.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.appsbygu.kadai_weather_java.R;
import com.appsbygu.kadai_weather_java.adapters.PinpointLocationAdapter;
import com.appsbygu.kadai_weather_java.databinding.ActivityWeatherBinding;
import com.appsbygu.kadai_weather_java.models.apis.Weathers.PinpointLocation;
import com.appsbygu.kadai_weather_java.models.apis.Weathers.Weather;
import com.appsbygu.kadai_weather_java.services.WeatherAPIService;

import io.realm.RealmList;


public class WeatherActivity extends AppCompatActivity {

    private WeatherAPIService weatherAPIService;
    private Handler handler;
    private ActivityWeatherBinding weatherBinding;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        String cityCode = intent.getStringExtra("cityCode");

        weatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        weatherAPIService = new WeatherAPIService();
        receiveWeatherInfo(cityCode);

    }

    private void receiveWeatherInfo(String _cityCode) {
        handler = new Handler() {
            public void handleMessage(Message msg){
                Weather weather = (Weather) msg.obj;

                displayTitle(weather);
                displayDiscription(weather);
                displayLocation(weather);
                setPinpointView(weather.getPinpointLocations());
            }
        };
        weatherAPIService.receive_weather_info(handler, _cityCode);
    }

    private void displayTitle(Weather weather) {
        TextView tv = (TextView)findViewById(R.id.titleTV);
        tv.setText(weather.getTitle().trim());
    }

    private void displayDiscription(Weather weather) {
        TextView description = (TextView)findViewById(R.id.descriptionTV);
        description.setText(weather.getDescription().getText());
    }

    private void displayLocation(Weather weather) {
        TextView location = (TextView)findViewById(R.id.locationTV);
        String locationStr = "";
        if(weather.getLocation().getArea() != null) locationStr += weather.getLocation().getArea();
        if(weather.getLocation().getPrefecture() != null) locationStr += " > " + weather.getLocation().getPrefecture();
        if(weather.getLocation().getCity() != null) locationStr += " > " + weather.getLocation().getCity();
        location.setText(locationStr);
    }

    private void setPinpointView(RealmList<PinpointLocation> pinpoints){
        weatherBinding.pinpointRV.setHasFixedSize(true);
        adapter = new PinpointLocationAdapter(pinpoints);
        weatherBinding.pinpointRV.setAdapter(adapter);
        weatherBinding.pinpointRV.setLayoutManager(new LinearLayoutManager(this));
    }
}
