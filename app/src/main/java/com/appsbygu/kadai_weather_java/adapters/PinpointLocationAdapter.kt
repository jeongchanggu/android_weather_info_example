package com.appsbygu.kadai_weather_java.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.appsbygu.kadai_weather_java.R
import com.appsbygu.kadai_weather_java.models.apis.Weathers.PinpointLocation

import io.realm.RealmList

class PinpointLocationAdapter(var pinpoints: RealmList<PinpointLocation>) : RecyclerView.Adapter<PinpointLocationAdapter.PinpointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinpointLocationAdapter.PinpointViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pinpoint_location, parent, false)
        return PinpointLocationAdapter.PinpointViewHolder(view)
    }

    override fun onBindViewHolder(holder: PinpointLocationAdapter.PinpointViewHolder, position: Int) {
        holder.ppNameTv.text = pinpoints[position]?.name
        holder.ppLinkTv.text = pinpoints[position]?.link
    }

    override fun getItemCount(): Int {
        return pinpoints.size
    }

    class PinpointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ppNameTv: TextView = itemView.findViewById<View>(R.id.ppNameTv) as TextView
        val ppLinkTv: TextView = itemView.findViewById<View>(R.id.ppLinkTv) as TextView

    }

}
