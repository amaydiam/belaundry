package com.akabetech.belaundryondemand.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.adapter.viewholder.ImageListViewHolder;

/**
 * Created by akbar.pambudi on 9/18/2016.
 */
public class ImageListViewAdapter extends RecyclerView.Adapter<ImageListViewHolder> {
    private String[] urls;


    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.recyrcle_layout_image_list,parent);
        return new ImageListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageListViewHolder holder, int position) {
        holder.bind(urls[position]);
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }
}
