package com.appsbygu.kadai_weather_java.activities

import android.databinding.DataBindingUtil
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.appsbygu.kadai_weather_java.R
import com.appsbygu.kadai_weather_java.adapters.AreaCodeAdapter
import com.appsbygu.kadai_weather_java.databinding.ActivityMainBinding
import com.appsbygu.kadai_weather_java.models.apis.Cities.Rss
import com.appsbygu.kadai_weather_java.services.WeatherAPIService

import io.realm.Realm
import io.realm.RealmConfiguration


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initRealm()
        receiveAreaCodesFromRealm()
    }

    private fun receiveAreaCodesFromRealm() {
        val rss = realm.where(Rss::class.java).findFirst()
        if (rss != null && rss.isAvailable()) {
            displayDataLoaded("Rss data loaded from RealmDB　")
            displayUpdatedTime(rss)
            setAreaCodeView(rss)
        } else {
            displayDataLoaded("Rss data loaded from XML")
            receiveAreaCodesFromXML()
        }
    }

    private fun receiveAreaCodesFromXML() {
        val weatherAPIService = WeatherAPIService()
        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                val rss = msg.obj as Rss
                displayUpdatedTime(rss)
                setAreaCodeView(rss)
                weatherAPIService.clearAreaCodeDB(realm)
                weatherAPIService.saveAreaCodeDB(realm, rss)
            }
        }

        weatherAPIService.receiveAreaCodes(handler)
    }

    private fun displayUpdatedTime(rss: Rss?) {
        rss?.channel?.lastBuildDate.let {
            val buildDateTv = findViewById<View>(R.id.buildDateTV) as TextView
            val dateText = "Update : $it"
            buildDateTv.text = dateText
        }
    }

    private fun displayDataLoaded(dataLoaded: String) {
        val dataLoadedFromTV = findViewById<View>(R.id.dataLoadedFromTV) as TextView
        dataLoadedFromTV.text = dataLoaded
    }

    /**
     * Set recyclerview　
     * @param rss
     */
    private fun setAreaCodeView(rss: Rss) {
        rss.channel?.source?.prefs?.let {
            mainBinding.areaCodeRV.setHasFixedSize(false)
            mainBinding.areaCodeRV.adapter = AreaCodeAdapter(it)
            mainBinding.areaCodeRV.layoutManager = LinearLayoutManager(this)
        }
    }

    /**
     * Initialize Realm Database
     */
    private fun initRealm() {
        Realm.init(this)

        // TODO: Delete development configuration
        val conf = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(conf)

        realm = Realm.getDefaultInstance()
    }


}