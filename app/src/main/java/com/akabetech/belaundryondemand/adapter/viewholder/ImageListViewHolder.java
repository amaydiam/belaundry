package com.akabetech.belaundryondemand.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.akabetech.belaundryondemand.R;
import com.squareup.picasso.Picasso;

/**
 * Created by akbar.pambudi on 9/18/2016.
 */
public class ImageListViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageItem;
    private Context context;
    public ImageListViewHolder(View itemView) {
        super(itemView);
        imageItem = (ImageView)itemView.findViewById(R.id.recyrcle_layout_image_content);
        context = itemView.getContext();
    }

    public void bind(String url){
        Picasso.with(context).load(url).placeholder(R.mipmap.placeholder).into(imageItem);
    }

}
