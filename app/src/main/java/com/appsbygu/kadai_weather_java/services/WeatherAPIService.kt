package com.appsbygu.kadai_weather_java.services

import android.os.Handler

import com.appsbygu.kadai_weather_java.interfaces.IWeatherService
import com.appsbygu.kadai_weather_java.models.apis.Cities.Channel
import com.appsbygu.kadai_weather_java.models.apis.Cities.City
import com.appsbygu.kadai_weather_java.models.apis.Cities.Pref
import com.appsbygu.kadai_weather_java.models.apis.Cities.Rss
import com.appsbygu.kadai_weather_java.models.apis.Cities.Source

import java.io.IOException

import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

const val BASE_URL = "http://weather.livedoor.com/"

class WeatherAPIService {
    private val rxAdapter: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    fun receiveAreaCodes(handler: Handler) {
        val retrofit = getRetrofit(SimpleXmlConverterFactory.create())
        val service = retrofit.create(IWeatherService::class.java)
        val call = service.listCitis()
        val msg = handler.obtainMessage()

        object : Thread() {
            override fun run() {
                try {
                    val response = call.execute()
                    msg.obj = response.body()

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                handler.sendMessage(msg)
            }
        }.start()
    }

    fun receiveWeatherInfo(handler: Handler, cityCode: String) {
        val retrofit = getRetrofit(GsonConverterFactory.create())
        val service = retrofit.create(IWeatherService::class.java)
        val call = service!!.weatherInfo(cityCode)
        val msg = handler.obtainMessage()

        object : Thread() {
            override fun run() {
                try {
                    val response = call.execute()
                    msg.obj = response.body()

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                handler.sendMessage(msg)
            }
        }.start()
    }

    fun clearAreaCodeDB(realm: Realm) {
        realm.executeTransaction { realm ->
            val rssData = realm.where(Rss::class.java).findAll()
            val prefData = realm.where(Pref::class.java).findAll()
            val cityData = realm.where(City::class.java).findAll()
            val channelData = realm.where(Channel::class.java).findAll()
            val sourceData = realm.where(Source::class.java).findAll()

            for (rss in rssData) rss.deleteFromRealm()
            for (pref in prefData) pref.deleteFromRealm()
            for (city in cityData) city.deleteFromRealm()
            for (channel in channelData) channel.deleteFromRealm()
            for (source in sourceData) source.deleteFromRealm()
        }

    }

    fun saveAreaCodeDB(_realm: Realm, _rss: Rss) {
        _realm.executeTransaction { realm ->
            _rss.savedTimeStamp = System.currentTimeMillis()
            realm.copyToRealm(_rss)
        }
    }


    private fun getRetrofit(factory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(factory)
                .addCallAdapterFactory(rxAdapter)
                .build()
    }

}
