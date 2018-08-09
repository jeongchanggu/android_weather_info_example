package com.appsbygu.kadai_weather_java.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.appsbygu.kadai_weather_java.R
import com.appsbygu.kadai_weather_java.activities.WeatherActivity
import com.appsbygu.kadai_weather_java.models.apis.cities.City
import com.appsbygu.kadai_weather_java.models.apis.cities.Pref
import io.realm.RealmObject

class AreaCodeAdapter(private var prefs: ArrayList<RealmObject>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> { PrefNameViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.area_codes_pref, parent,false)) }
            else -> {
                CityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.area_codes_city, parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            0 -> {
                holder as PrefNameViewHolder
                holder.prefNameTextView.text = (prefs[position] as Pref).title ?: ""
                holder.prefNameTextView.visibility = View.VISIBLE
            }
            else -> {
                val context = holder.itemView.context
                val viewHolder = holder as CityViewHolder
                val city = prefs[position] as City

                viewHolder.cityNameTextView.text = city.title
                viewHolder.cityNameTextView.setOnClickListener {
                    val intent = WeatherActivity.createIntent(context, city)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return prefs.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (prefs[position] is Pref) 0 else 1
    }

    class PrefNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prefNameTextView: TextView = itemView.findViewById(R.id.prefNameTextView)
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityNameTextView: TextView = itemView.findViewById(R.id.cityNameTextView)
    }
}
