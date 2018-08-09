package com.appsbygu.kadai_weather_java.activities

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast

import com.appsbygu.kadai_weather_java.R
import com.appsbygu.kadai_weather_java.adapters.PinpointLocationAdapter
import com.appsbygu.kadai_weather_java.databinding.ActivityWeatherBinding
import com.appsbygu.kadai_weather_java.models.apis.cities.City
import com.appsbygu.kadai_weather_java.models.apis.weathers.Location
import com.appsbygu.kadai_weather_java.models.apis.weathers.PinpointLocation
import com.appsbygu.kadai_weather_java.models.apis.weathers.Weather
import com.appsbygu.kadai_weather_java.services.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.converter.gson.GsonConverterFactory


class WeatherActivity : AppCompatActivity() {

    companion object {

        private const val CITY_CODE = "cityCode"

        fun createIntent(context: Context, city: City): Intent{
            return Intent(context, WeatherActivity::class.java)
                    .putExtra(CITY_CODE, city.id!!.toString())
        }

    }

    private lateinit var weatherBinding: ActivityWeatherBinding
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        supportActionBar?.hide()
        weatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather)

        val cityCode = intent.getStringExtra("cityCode")

        fetchWeatherInfo(cityCode)

    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun fetchWeatherInfo(cityCode: String) {

        val service = WeatherAPIService.getService(GsonConverterFactory.create())
        val dispose = service.weatherInfo(cityCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::afterWeatherInfoLoaded, this::weatherInfoLoadFail)
        disposables.add(dispose)
    }

    private fun afterWeatherInfoLoaded(weather: Weather) {
        showTitle(weather)
        showDescription(weather)
        showLocation(weather.location)
        setPinpointView(weather.pinpointLocations)
    }

    private fun weatherInfoLoadFail(error: Throwable) {
        val toast = Toast.makeText(applicationContext, getString(R.string.data_load_error), Toast.LENGTH_LONG)
        toast.show()
    }

    private fun showTitle(weather: Weather) {
        titleTextView.text = weather.title?.trim { it <= ' ' } ?: ""
    }

    private fun showDescription(weather: Weather) {
        descriptionTextView.text = weather.description?.text ?: ""
    }

    private fun showLocation(location: Location?) {
        var locationStr = ""
        location?.area?.let { locationStr += it }
        location?.prefecture?.let { locationStr += " > $it" }
        location?.city?.let { locationStr += " > $it" }
        locationTextView.text = locationStr
    }

    private fun setPinpointView(pinpoints: RealmList<PinpointLocation>?) {
        pinpoints?.let {
            weatherBinding.pinpointRV.setHasFixedSize(true)
            weatherBinding.pinpointRV.adapter = PinpointLocationAdapter(it)
            weatherBinding.pinpointRV.layoutManager = LinearLayoutManager(this)
        }
    }
}
