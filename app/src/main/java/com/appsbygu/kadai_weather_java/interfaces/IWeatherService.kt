package com.appsbygu.kadai_weather_java.interfaces

import com.appsbygu.kadai_weather_java.models.apis.Cities.Rss
import com.appsbygu.kadai_weather_java.models.apis.Weathers.Weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherService {
    @GET("forecast/rss/primary_area.xml")
    fun listCitis(): Call<Rss>

    @GET("forecast/webservice/json/v1")
    fun weatherInfo(@Query("city") city_code: String): Call<Weather>
}
