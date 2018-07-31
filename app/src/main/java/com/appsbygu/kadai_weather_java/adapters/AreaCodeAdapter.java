package com.appsbygu.kadai_weather_java.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsbygu.kadai_weather_java.R;
import com.appsbygu.kadai_weather_java.activities.WeatherActivity;
import com.appsbygu.kadai_weather_java.models.apis.Cities.City;
import com.appsbygu.kadai_weather_java.models.apis.Cities.Pref;

import io.realm.RealmList;

public class AreaCodeAdapter extends RecyclerView.Adapter<AreaCodeAdapter.PrefNameViewHolder>{

    RealmList<Pref> prefs;

    public AreaCodeAdapter(RealmList<Pref> prefs) {
        this.prefs = prefs;
    }

    @Override
    public PrefNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_codes, parent, false);
        return new PrefNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PrefNameViewHolder holder, int position){
        holder.prefNameTv.setText(prefs.get(position).getTitle());

        final Context context = holder.itemView.getContext();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout_childItems = holder.ll_child;
        linearLayout_childItems.removeAllViews();

        for (int indexView = 0; indexView < prefs.get(position).getCities().size() ; indexView++) {

            final City city = prefs.get(position).getCities().get(indexView);
            TextView cityTV = createCityTV(context, city, indexView);
            linearLayout_childItems.addView(cityTV, layoutParams);

        }
    }

    @Override
    public int getItemCount() {
        return prefs.size();
    }

    class PrefNameViewHolder extends RecyclerView.ViewHolder {
        private TextView prefNameTv;
        private LinearLayout ll_child;

        public PrefNameViewHolder(View itemView){
            super(itemView);
            prefNameTv = (TextView) itemView.findViewById(R.id.prefNameTv);
            ll_child = (LinearLayout) itemView.findViewById(R.id.ll_child);
        }
    }

    private TextView createCityTV(final Context context, final City city, int id){
        final TextView textView = new TextView(context);
        textView.setId(id);
        textView.setPadding(130, 20, 0, 20);
        textView.setGravity(Gravity.LEFT);
        textView.setText(city.getTitle());
        textView.setBackgroundResource(R.drawable.border_right_small);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WeatherActivity.class);
                String message = city.getId().toString();
                intent.putExtra("cityCode", message);
                Activity ma = (Activity) context;
                ma.startActivity(intent);
            }
        });
        return textView;
    }
}
