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
import retrofit2.Call
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
        apiThreadHandler(call, handler)
    }

    fun receiveWeatherInfo(handler: Handler, cityCode: String) {
        val retrofit = getRetrofit(GsonConverterFactory.create())
        val service = retrofit.create(IWeatherService::class.java)
        val call = service.weatherInfo(cityCode)
        apiThreadHandler(call, handler)
    }


    fun clearAreaCodeDB(realm: Realm) {
        realm.executeTransaction { realm ->
            realm.where(Rss::class.java).findAll().deleteAllFromRealm()
            realm.where(Pref::class.java).findAll().deleteAllFromRealm()
            realm.where(City::class.java).findAll().deleteAllFromRealm()
            realm.where(Channel::class.java).findAll().deleteAllFromRealm()
            realm.where(Source::class.java).findAll().deleteAllFromRealm()
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

    private fun apiThreadHandler(call: Call<*>, handler: Handler) {
        val msg = handler.obtainMessage()
        object : Thread() {
            override fun run() {
                try {
                    msg.obj = call.execute().body()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                handler.sendMessage(msg)
            }
        }.start()
    }


}
