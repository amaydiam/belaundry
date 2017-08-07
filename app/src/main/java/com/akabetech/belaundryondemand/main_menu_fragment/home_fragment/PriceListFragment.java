package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.adapter.PriceListExpandableAdapter;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.core.enumeration.PriceListBtnStatus;
import com.akabetech.belaundryondemand.model.PriceListChild;
import com.akabetech.belaundryondemand.model.PriceListType;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliItemStoreAdapter;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliPackageItemStoreAdapter;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliPackageKiloanStoreAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.KiloanPrice;
import com.akabetech.belaundryondemand.retrofit.model.beans.PackageItem;
import com.akabetech.belaundryondemand.retrofit.model.beans.PerItemPrice;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;
import com.akabetech.belaundryondemand.ui.dialog.StoreDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceListFragment extends Fragment {

    @BindView(R.id.expandable_price)
    ExpandableListView expandablePrice;
    @BindView(R.id.pgLoading)
    ProgressBar progressBar;
    public PriceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_price_list, container, false);
        v.setBackgroundColor(Color.WHITE);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showHideLoading(true);
        if (getSelectedStore() != null)
            providePriceListThread();
    }

    private Call<List<KiloanPrice>> provideKiloanPrices(){

        return AdapterComponent.getAdapter(BeAsliPackageKiloanStoreAdapter.class).getKiloanPricesByStoreCode(getSelectedStore().getCode());

    }

    private Call<List<PerItemPrice>> providePackageItemPrices(){
        return AdapterComponent.getAdapter(BeAsliPackageItemStoreAdapter.class).getPerItemPricesByStoreCode(getSelectedStore().getCode());
    }

    private Call<List<PackageItem>> providePackages(){
        return AdapterComponent.getAdapter(BeAsliItemStoreAdapter.class).getPackageListByStoreCode(getSelectedStore().getCode());
    }

    private Store getSelectedStore(){
        return (Store) getArguments().getParcelable(StoreDialog.STORE);
    }

    private void providePriceListThread(){

                final List<PriceListType> priceListTypes = new ArrayList<>();
                final List<PriceListChild> priceListChildren = new ArrayList<>();

                provideKiloanPrices().enqueue(new Callback<List<KiloanPrice>>() {
                @Override
                public void onResponse(Call<List<KiloanPrice>> call, Response<List<KiloanPrice>> response) {
                    if(response.code() == 200){
                        priceListChildren.add(new PriceListChild(response.body(), null, null, R.layout.kiloan_layout));
                        PriceListType kiloanPriceListType = new PriceListType("Kiloan", PriceListBtnStatus.EXPAND);
                        kiloanPriceListType.setChildren(priceListChildren);
                        priceListTypes.add(kiloanPriceListType);
                        perPackageDataStep(priceListTypes);
                    }
                }
                @Override
                public void onFailure(Call<List<KiloanPrice>> call, Throwable t) {

                }
            });


    }

    private void showHideLoading(boolean show){
        if(!show){
            progressBar.setVisibility(View.GONE);
            expandablePrice.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.VISIBLE);
            expandablePrice.setVisibility(View.GONE);
        }
    }

    private void perPackageDataStep(final List<PriceListType> priceListTypes){
        providePackages().enqueue(new Callback<List<PackageItem>>() {
            @Override
            public void onResponse(Call<List<PackageItem>> call, final Response<List<PackageItem>> response) {
                if(response.code()==200)
                {
                    final List<PackageItem> listpackage = response.body();
                    providePackageItemPrices().enqueue(new Callback<List<PerItemPrice>>() {
                        @Override
                        public void onResponse(Call<List<PerItemPrice>> _call, Response<List<PerItemPrice>> _response) {
                            if(_response.code() ==200){
                                final List<PriceListChild> priceListChildren = new ArrayList<>();
                                priceListChildren.add(new PriceListChild(null,_response.body(), listpackage, R.layout.price_list_perunit));
                                PriceListType priceListType = new PriceListType("Per Package", PriceListBtnStatus.EXPAND);
                                priceListType.setChildren(priceListChildren);
                                priceListTypes.add(priceListType);
                                addToAdapter(priceListTypes);
                            }

                        }

                        @Override
                        public void onFailure(Call<List<PerItemPrice>> _call, Throwable _t) {

                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<List<PackageItem>> call, Throwable t) {

            }
        });



    }

    public void addToAdapter(List<PriceListType> priceListTypes){
        showHideLoading(false);
        PriceListExpandableAdapter priceListAdapter = new PriceListExpandableAdapter(priceListTypes, getContext());
        expandablePrice.setAdapter(priceListAdapter);

    }
}
