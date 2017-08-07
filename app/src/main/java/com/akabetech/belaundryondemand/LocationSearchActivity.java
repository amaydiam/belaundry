package com.akabetech.belaundryondemand;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.GeoCoderAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.GeoCoderAddress;
import com.akabetech.belaundryondemand.retrofit.model.beans.GeocoderResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationSearchActivity extends AppCompatActivity {
    @BindView(R.id.lv_search)
    ListView listView;

    List<GeoCoderAddress> dataset = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        ButterKnife.bind(this);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{}));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("address",dataset.get(position));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.location_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) LocationSearchActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    findAddress(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    findAddress(newText);
                    return false;
                }
            });
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(LocationSearchActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void findAddress(String address){
        AdapterComponent.getGoogleServiceAdapter(GeoCoderAdapter.class).getGeocodeFromAddress(address,"country:ID").enqueue(new Callback<GeocoderResult>() {
            @Override
            public void onResponse(Call<GeocoderResult> call, Response<GeocoderResult> response) {
                if (response.body() != null && response.code() == 200) {
                   String[] addresses = new String[response.body().getGeoCoderAddresses().size()];
                    int i = 0;
                    for(GeoCoderAddress adr : response.body().getGeoCoderAddresses()){
                        addresses[i] = adr.getAddress();
                        i++;
                    }
                    dataset = response.body().getGeoCoderAddresses();
                    listView.setAdapter(new ArrayAdapter<String>(LocationSearchActivity.this,android.R.layout.simple_list_item_1,addresses));
                } else {

                    listView.setAdapter(new ArrayAdapter<String>(LocationSearchActivity.this,android.R.layout.simple_list_item_1,new String[]{}));

                }
                Log.i(LocationSearchActivity.class.getName()+".Debug", call.request().url().toString());
            }

            @Override
            public void onFailure(Call<GeocoderResult> call, Throwable t) {
                Toast.makeText(LocationSearchActivity.this, call.request().url().toString() + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
