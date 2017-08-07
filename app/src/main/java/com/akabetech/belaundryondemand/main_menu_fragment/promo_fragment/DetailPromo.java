package com.akabetech.belaundryondemand.main_menu_fragment.promo_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.retrofit.model.beans.Promo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mrari on 5/21/2017.
 */

public class DetailPromo extends AppCompatActivity {
    public static Promo instance;

    public static Promo getInstance() {
        return instance;
    }

    public static void setInstance(Promo instance) {
        DetailPromo.instance = instance;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.startDatePromo)
    TextView startDatePromo;
    @BindView(R.id.endDatePromo)
    TextView endDatePromo;
    @BindView(R.id.codePromo)
    TextView codePromo;
    @BindView(R.id.descPromo)
    TextView descPromo;
    @BindView(R.id.iv_promo)
    ImageView iv_promo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_promo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbar_title.setText("Promo Detail");
        getDataFromApi();
    }

    private void getDataFromApi() {
        codePromo.setText(DetailPromo.getInstance().getCode_promo());
        descPromo.setText(DetailPromo.getInstance().getDescription());
        endDatePromo.setText(DetailPromo.getInstance().getExpired_date());
        Picasso.with(this).load("http://owner.belaundry.co.id/assets/images/promo/"+DetailPromo.getInstance().getPoster()).placeholder(R.mipmap.placeholder).into(iv_promo);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(item));
    }
}
