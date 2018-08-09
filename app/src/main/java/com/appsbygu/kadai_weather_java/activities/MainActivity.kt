package com.appsbygu.kadai_weather_java.activities

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast

import com.appsbygu.kadai_weather_java.R
import com.appsbygu.kadai_weather_java.adapters.AreaCodeAdapter
import com.appsbygu.kadai_weather_java.databinding.ActivityMainBinding
import com.appsbygu.kadai_weather_java.models.apis.cities.City
import com.appsbygu.kadai_weather_java.models.apis.cities.Pref
import com.appsbygu.kadai_weather_java.models.apis.cities.Rss
import com.appsbygu.kadai_weather_java.services.RealmService
import com.appsbygu.kadai_weather_java.services.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var realm: Realm
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        realm = Realm.getDefaultInstance()
        fetchAreaCodesFromRealm()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun fetchAreaCodesFromRealm() {
        val rss = realm.where(Rss::class.java).findFirst()
        if (rss != null && rss.isAvailable()) {
            showDataLoaded(getString(R.string.rss_data_from_realm))
            showUpdatedTime(rss)
            setAreaCodeView(rss)
        } else {
            showDataLoaded(getString(R.string.rss_data_from_xml))
            fetchAreaCodesFromXML()
        }
    }

    private fun fetchAreaCodesFromXML() {
        val service = WeatherAPIService.getService(SimpleXmlConverterFactory.create())
        val dispose = service.listCitis()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::afterFetchAreaCodeLoad, this::areaCodeLoadFail)
        disposables.add(dispose)
    }

    private fun afterFetchAreaCodeLoad(rss: Rss) {
        showUpdatedTime(rss)
        setAreaCodeView(rss)
        RealmService.clearAreaCodeDB()
        RealmService.saveAreaCodeDB(rss)
    }

    private fun areaCodeLoadFail(error: Throwable) {
        val toast = Toast.makeText(applicationContext, getString(R.string.data_load_error), Toast.LENGTH_LONG)
        toast.show()
    }

    private fun showUpdatedTime(rss: Rss?) {
        rss?.channel?.lastBuildDate?.let {
            val dateText = "${getString(R.string.show_update_prefix)} : $it"
            buildDateTextView.text = dateText
        }
    }

    private fun showDataLoaded(dataLoaded: String) {
        dataLoadedFromTextView.text = dataLoaded
    }

    /**
     * Set recyclerview　
     * @param rss
     */
    private fun setAreaCodeView(rss: Rss) {
        rss.channel?.source?.prefs?.let {
            mainBinding.areaCodeRecyclerView.setHasFixedSize(false)
            mainBinding.areaCodeRecyclerView.adapter = AreaCodeAdapter(createDataArray(it))
            mainBinding.areaCodeRecyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun createDataArray(prefs: RealmList<Pref>): ArrayList<RealmObject>{
        val entities = ArrayList<RealmObject>()
        for(pref in 0 until prefs.size) {
            entities.add(prefs[pref] as Pref)

            val citiesCnt = prefs[pref]?.cities?.size ?: 0
            for(city in 0 until citiesCnt) {
                entities.add(prefs[pref]!!.cities!![city] as City)
            }
        }
        return entities
    }

}