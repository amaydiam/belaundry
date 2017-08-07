package com.akabetech.belaundryondemand.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.akabetech.belaundryondemand.retrofit.model.beans.GeoCoderAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akbar.pambudi on 9/12/2016.
 */
public class GeoCoderAutoTextAdapter extends ArrayAdapter<GeoCoderAddress> implements Filterable {
    private List<GeoCoderAddress> geoCoderAddresses;

    public GeoCoderAutoTextAdapter(Context context, int resource) {
        super(context, resource);
        this.geoCoderAddresses = new ArrayList<>();
        this.add(new GeoCoderAddress("surabaya"));
    }

    @Override
    public int getCount() {
        return this.geoCoderAddresses.size();
    }

    @Override
    public GeoCoderAddress getItem(int position) {
        return this.geoCoderAddresses.get(position);
    }

   @Override
 public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults filterResults = new FilterResults();
                if(constraint!=null){

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results!=null && results.count>0){
                    notifyDataSetChanged();
                }else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
