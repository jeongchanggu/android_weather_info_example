package com.appsbygu.kadai_weather_java.services

import com.appsbygu.kadai_weather_java.models.apis.cities.Rss
import io.realm.Realm

class RealmService {
    companion object {
        fun clearAreaCodeDB() {
            Realm.getDefaultInstance().executeTransaction { realm ->
                realm.deleteAll()
            }
        }

        fun saveAreaCodeDB(rss: Rss) {
            Realm.getDefaultInstance().executeTransaction { realm ->
                rss.savedTimeStamp = System.currentTimeMillis()
                realm.copyToRealm(rss)
            }
        }
    }
}