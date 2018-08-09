package com.appsbygu.kadai_weather_java.services

import com.appsbygu.kadai_weather_java.interfaces.IWeatherService

import io.reactivex.schedulers.Schedulers
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

//import retrofit2.converter.jaxb.JaxbConverterFactory

const val BASE_URL = "http://weather.livedoor.com/"

class WeatherAPIService {

    companion object {
        private val rxAdapter: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        fun getService(factory: Converter.Factory): IWeatherService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(factory)
                    .addCallAdapterFactory(rxAdapter)
                    .build()
                    .create(IWeatherService::class.java)
        }
    }

}
