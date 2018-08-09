package com.appsbygu.kadai_weather_java.application

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        val conf = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(conf)
    }

}