package com.akabetech.belaundryondemand.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.core.enumeration.PriceListBtnStatus;
import com.akabetech.belaundryondemand.model.PriceListChild;
import com.akabetech.belaundryondemand.model.PriceListType;
import com.akabetech.belaundryondemand.retrofit.model.beans.KiloanPrice;
import com.akabetech.belaundryondemand.retrofit.model.beans.PackageItem;
import com.akabetech.belaundryondemand.retrofit.model.beans.PerItemPrice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akbar.pambudi on 8/24/2016.
 */
public class PriceListExpandableAdapter extends BaseExpandableListAdapter {

    private List<PriceListType> priceListTypes = new ArrayList<>();
    private Context context;
    public PriceListExpandableAdapter(List<PriceListType> priceListTypes,Context context) {
        this.priceListTypes = priceListTypes;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return priceListTypes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return priceListTypes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return priceListTypes.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    public String numberTocurrency(Double nilai)
    {
//        double x =20000;
        String currencyUI;
        DecimalFormat format = new DecimalFormat("###,###,###,###,###");
        currencyUI = format.format(nilai);
        currencyUI = currencyUI.replace(",", ".");
        return currencyUI;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null) convertView = View.inflate(context, R.layout.pricelist_parent,null);
        ((TextView)convertView.findViewById(R.id.tvPriceListParent)).setText(priceListTypes.get(groupPosition).getName());
        if(isExpanded){
            ((TextView)convertView.findViewById(R.id.btnPriceListParent)).setText(PriceListBtnStatus.COLLAPSE.getName());
        }else{
            ((TextView)convertView.findViewById(R.id.btnPriceListParent)).setText(PriceListBtnStatus.EXPAND.getName());
        }
        return convertView;
    }

    @Override
    public int getChildTypeCount() {
        return 2;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PriceListChild priceListChild = priceListTypes.get(groupPosition).getChildren().get(childPosition);
        convertView = LayoutInflater.from(context).inflate(priceListChild.getLayout(),null);
        if(priceListChild.getLayout() == R.layout.kiloan_layout){
            List<String[]> list = new ArrayList<>();
            for(KiloanPrice p :priceListChild.getKiloanPrices()){
                String packagePrice = numberTocurrency(Double.valueOf(p.getPackagePrice()));
                list.add(new String[]{p.getPackageName(),"IDR "+packagePrice});
            }
            ((RecyclerView)convertView.findViewById(R.id.listPriceKiloan)).setAdapter(new SimpleTwoFieldAdapter(list));
            ((RecyclerView)convertView.findViewById(R.id.listPriceKiloan)).setLayoutManager(new LinearLayoutManager(context));
        }else if(priceListChild.getLayout() == R.layout.price_list_perunit){
            List<String[]> packages = new ArrayList<>();
            List<String[]> packagesUnitPrice = new ArrayList<>();
            for(PackageItem pack :priceListChild.getPackageItems()){
                packages.add(new String[]{pack.getPackageName(),pack.getPackagePercentage()+" %"});
            }
            for(PerItemPrice perItem : priceListChild.getPerItemPrices()){
                String clothesPrice = numberTocurrency(Double.valueOf(perItem.getUnitClothesPrice()));
                packagesUnitPrice.add(new String[]{perItem.getUnitClothesName(),"IDR "+clothesPrice});
            }
            ((RecyclerView)convertView.findViewById(R.id.perUnitList)).setAdapter(new SimpleTwoFieldAdapter(packages));
            ((RecyclerView)convertView.findViewById(R.id.perUnitList)).setLayoutManager(new LinearLayoutManager(context));
            ((RecyclerView)convertView.findViewById(R.id.perUnitList)).setNestedScrollingEnabled(false);
            ((RecyclerView)convertView.findViewById(R.id.pricePerUnitList)).setNestedScrollingEnabled(false);
            ((RecyclerView)convertView.findViewById(R.id.pricePerUnitList)).setAdapter(new SimpleTwoFieldAdapter(packagesUnitPrice));
            ((RecyclerView)convertView.findViewById(R.id.pricePerUnitList)).setLayoutManager(new LinearLayoutManager(context));


        }
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
