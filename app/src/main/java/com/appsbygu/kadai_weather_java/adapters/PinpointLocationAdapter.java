package com.appsbygu.kadai_weather_java.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsbygu.kadai_weather_java.R;
import com.appsbygu.kadai_weather_java.models.apis.Weathers.PinpointLocation;

import io.realm.RealmList;

public class PinpointLocationAdapter extends RecyclerView.Adapter<PinpointLocationAdapter.PinpointViewHolder>{

    RealmList<PinpointLocation> pinpoints;

    public PinpointLocationAdapter(RealmList<PinpointLocation> pinpoints) {
        this.pinpoints = pinpoints;
    }

    @Override
    public PinpointLocationAdapter.PinpointViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinpoint_location, parent, false);
        return new PinpointLocationAdapter.PinpointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PinpointLocationAdapter.PinpointViewHolder holder, int position){
        holder.ppNameTv.setText(pinpoints.get(position).getName());
        holder.ppLinkTv.setText(pinpoints.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return pinpoints.size();
    }

    class PinpointViewHolder extends RecyclerView.ViewHolder {
        private TextView ppNameTv;
        private TextView ppLinkTv;

        public PinpointViewHolder(View itemView){
            super(itemView);
            ppNameTv = (TextView) itemView.findViewById(R.id.ppNameTv);
            ppLinkTv = (TextView) itemView.findViewById(R.id.ppLinkTv);
        }
    }

}
