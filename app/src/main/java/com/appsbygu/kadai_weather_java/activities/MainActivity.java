package com.appsbygu.kadai_weather_java.activities;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.appsbygu.kadai_weather_java.R;
import com.appsbygu.kadai_weather_java.adapters.AreaCodeAdapter;
import com.appsbygu.kadai_weather_java.databinding.ActivityMainBinding;
import com.appsbygu.kadai_weather_java.models.apis.Cities.Pref;
import com.appsbygu.kadai_weather_java.models.apis.Cities.Rss;
import com.appsbygu.kadai_weather_java.services.WeatherAPIService;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity{

    private WeatherAPIService weatherAPIService;
    private Handler handler;
    private ActivityMainBinding mainBinding;
    private RecyclerView.Adapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        weatherAPIService = new WeatherAPIService();
        initRealm();
        receiveAreaCodesFromRealm();
    }

    private void receiveAreaCodesFromRealm() {
        final RealmResults<Rss> rss = realm.where(Rss.class).findAll();
        if(rss.size() != 0 && rss.first().isAvailable()){
            displayDataLoaded("Rss data loaded from RealmDB　");
            displayUpdatedTime(rss.first());
            setAreaCodeView(rss.first());
        }else{
            displayDataLoaded("Rss data loaded from XML");
            receiveAreaCodesFromXML();
        }
    }

    private void receiveAreaCodesFromXML() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                Rss rss = (Rss) msg.obj;
                displayUpdatedTime(rss);
                setAreaCodeView(rss);
                weatherAPIService.clearAreaCodeDB(realm);
                weatherAPIService.saveAreaCodeDB(realm, rss);
            }
        };

        weatherAPIService.receive_area_codes(handler);
    }

    private void displayUpdatedTime(Rss rss) {
        TextView buildDateTv = (TextView) findViewById(R.id.buildDateTV);
        buildDateTv.setText("Update : "+rss.getChannel().getLastBuildDate().toString());
    }

    private void displayDataLoaded(String dataLoaded) {
        TextView dataLoadedFromTV = (TextView) findViewById(R.id.dataLoadedFromTV);
        dataLoadedFromTV.setText(dataLoaded);
    }

    /**
     * Set recyclerview　
     * @param rss
     */
    private void setAreaCodeView(Rss rss) {
        RealmList<Pref> pref = rss.getChannel().getSource().getPrefs();
        mainBinding.areaCodeRV.setHasFixedSize(false);
        adapter = new AreaCodeAdapter(pref);
        mainBinding.areaCodeRV.setAdapter(adapter);
        mainBinding.areaCodeRV.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Initialize Realm Database
     */
    private void initRealm() {
        Realm.init(this);

        // TODO: Delete development configuration
        RealmConfiguration conf = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(conf);

        realm = Realm.getDefaultInstance();
    }


}