package com.akabetech.belaundryondemand.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.adapter.viewholder.SimpleTwoFieldViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akbar.pambudi on 8/22/2016.
 */
public class SimpleTwoFieldAdapter extends RecyclerView.Adapter<SimpleTwoFieldViewHolder> {

    private List<String[]> list = new ArrayList<>();

    public SimpleTwoFieldAdapter(List<String[]> list) {
        this.list = list;
    }

    @Override
    public SimpleTwoFieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_twofield_adapter_layout,parent,false);
        return new SimpleTwoFieldViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SimpleTwoFieldViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
