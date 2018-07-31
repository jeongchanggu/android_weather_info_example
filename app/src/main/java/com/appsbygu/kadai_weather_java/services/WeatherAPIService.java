package com.appsbygu.kadai_weather_java.services;

import android.os.Handler;
import android.os.Message;

import com.appsbygu.kadai_weather_java.interfaces.IWeatherService;
import com.appsbygu.kadai_weather_java.models.apis.Cities.Channel;
import com.appsbygu.kadai_weather_java.models.apis.Cities.City;
import com.appsbygu.kadai_weather_java.models.apis.Cities.Pref;
import com.appsbygu.kadai_weather_java.models.apis.Cities.Rss;
import com.appsbygu.kadai_weather_java.models.apis.Cities.Source;
import com.appsbygu.kadai_weather_java.models.apis.Weathers.Weather;

import java.io.IOException;

import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class WeatherAPIService {

    public static final String BASE_URL = "http://weather.livedoor.com/";
    private RxJava2CallAdapterFactory rxAdapter;
    private IWeatherService service;


    public WeatherAPIService() {
        rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }


    public void receive_area_codes(final Handler handler) {
        Retrofit retrofit = get_retrofit(SimpleXmlConverterFactory.create());
        service = retrofit.create(IWeatherService.class);
        final Call<Rss> call = service.listCitis();
        final Message msg = handler.obtainMessage();

        new Thread() {
            public void run() {
                try {
                    Response<Rss> response = call.execute();
                    msg.obj = response.body();

                } catch (IOException e ){
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    public void receive_weather_info(final Handler handler, final String cityCode) {
        Retrofit retrofit = get_retrofit(GsonConverterFactory.create());
        service = retrofit.create(IWeatherService.class);
        final Call<Weather> call = service.weatherInfo(cityCode);
        final Message msg = handler.obtainMessage();

        new Thread() {
            public void run() {
                try {
                    Response<Weather> response = call.execute();
                    msg.obj = response.body();

                } catch (IOException e ){
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    public void clearAreaCodeDB(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(Realm realm) {
            RealmResults<Rss> rssData = realm.where(Rss.class).findAll();
            RealmResults<Pref> prefData = realm.where(Pref.class).findAll();
            RealmResults<City> cityData = realm.where(City.class).findAll();
            RealmResults<Channel> channelData = realm.where(Channel.class).findAll();
            RealmResults<Source> sourceData = realm.where(Source.class).findAll();

            for(Rss rss: rssData) rss.deleteFromRealm();
            for(Pref pref: prefData) pref.deleteFromRealm();
            for(City city: cityData) city.deleteFromRealm();
            for(Channel channel: channelData) channel.deleteFromRealm();
            for(Source source: sourceData) source.deleteFromRealm();
          }
        });

    }

    public void saveAreaCodeDB(final Realm _realm, final Rss _rss) {
        _realm.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(Realm realm) {
            _rss.setSavedTimeStamp(System.currentTimeMillis());
            realm.copyToRealm(_rss);
          }
        });
    }


    private Retrofit get_retrofit(Converter.Factory factory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(factory)
                .addCallAdapterFactory(rxAdapter)
                .build();
        return retrofit;
    }

}
