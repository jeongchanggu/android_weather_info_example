package com.appsbygu.kadai_weather_java.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.appsbygu.kadai_weather_java.R
import com.appsbygu.kadai_weather_java.activities.WeatherActivity
import com.appsbygu.kadai_weather_java.models.apis.Cities.City
import com.appsbygu.kadai_weather_java.models.apis.Cities.Pref

import io.realm.RealmList

class AreaCodeAdapter(var prefs: RealmList<Pref>) : RecyclerView.Adapter<AreaCodeAdapter.PrefNameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrefNameViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.area_codes, parent, false)
        return PrefNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrefNameViewHolder, position: Int) {
        holder.prefNameTv.text = prefs[position]?.title ?: ""

        val context = holder.itemView.context
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val linearLayoutChildItems = holder.llChild

        linearLayoutChildItems.removeAllViews()

        var citiesCnt = prefs[position]?.cities?.size ?: 0
        for (indexView in 0 until citiesCnt) {
            prefs[position]?.cities?.let {
                it[indexView]?.let {
                    val cityTV = createCityTV(context, it, indexView)
                    linearLayoutChildItems.addView(cityTV, layoutParams)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return prefs.size
    }

    class PrefNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val prefNameTv: TextView = itemView.findViewById<View>(R.id.prefNameTv) as TextView
        val llChild: LinearLayout = itemView.findViewById<View>(R.id.ll_child) as LinearLayout

    }

    private fun createCityTV(context: Context, city: City, id: Int): TextView {
        val textView = TextView(context)
        textView.id = id
        textView.setPadding(130, 20, 0, 20)
        textView.gravity = Gravity.LEFT
        textView.text = city.title
        textView.setBackgroundResource(R.drawable.border_right_small)
        textView.setTextColor(Color.parseColor("#ffffff"))
        textView.setOnClickListener {
            val intent = Intent(context, WeatherActivity::class.java)
            val message = city.id!!.toString()
            intent.putExtra("cityCode", message)
            val ma = context as Activity
            ma.startActivity(intent)
        }
        return textView
    }
}
