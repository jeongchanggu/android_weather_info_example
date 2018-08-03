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
import com.appsbygu.kadai_weather_java.adapters.PinpointLocationAdapter
import com.appsbygu.kadai_weather_java.databinding.ActivityWeatherBinding
import com.appsbygu.kadai_weather_java.models.apis.Weathers.Location
import com.appsbygu.kadai_weather_java.models.apis.Weathers.PinpointLocation
import com.appsbygu.kadai_weather_java.models.apis.Weathers.Weather
import com.appsbygu.kadai_weather_java.services.WeatherAPIService

import io.realm.RealmList


class WeatherActivity : AppCompatActivity() {

    private var weatherAPIService: WeatherAPIService? = null
    private var handler: Handler? = null
    private var weatherBinding: ActivityWeatherBinding? = null
    private var adapter: RecyclerView.Adapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        supportActionBar!!.hide()

        val intent = intent
        val cityCode = intent.getStringExtra("cityCode")

        weatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        weatherAPIService = WeatherAPIService()
        receiveWeatherInfo(cityCode)

    }

    private fun receiveWeatherInfo(_cityCode: String) {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                val weather = msg.obj as Weather

                displayTitle(weather)
                displayDiscription(weather)
                displayLocation(weather.location)
                setPinpointView(weather.pinpointLocations)
            }
        }
        weatherAPIService!!.receiveWeatherInfo(handler!!, _cityCode)
    }

    private fun displayTitle(weather: Weather) {
        val tv = findViewById<View>(R.id.titleTV) as TextView
        tv.text = weather?.title?.trim { it <= ' ' } ?: ""
    }

    private fun displayDiscription(weather: Weather) {
        val description = findViewById<View>(R.id.descriptionTV) as TextView
        description.text = weather.description?.text ?: ""
    }

    private fun displayLocation(location: Location?) {
        val locationView = findViewById<View>(R.id.locationTV) as TextView
        var locationStr = ""

        location?.area?.let { locationStr += it }
        location?.prefecture?.let { locationStr += " > $it" }
        location?.city?.let { locationStr += " > $it" }

        locationView.text = locationStr
    }

    private fun setPinpointView(pinpoints: RealmList<PinpointLocation>?) {
        weatherBinding!!.pinpointRV.setHasFixedSize(true)
        adapter = PinpointLocationAdapter(pinpoints!!)
        weatherBinding!!.pinpointRV.adapter = adapter
        weatherBinding!!.pinpointRV.layoutManager = LinearLayoutManager(this)
    }
}
