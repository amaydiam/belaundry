package com.akabetech.belaundryondemand.main_menu_fragment.my_account_fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

import net.glxn.qrgen.android.QRCode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mrari on 6/1/2017.
 */

public class ShowQrCode extends AppCompatActivity {
    @BindView(R.id.imvQrCode)
    ImageView qrcode;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_qrcode);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);
        Bitmap qrcodeBitmap = generateQRCode();
        qrcode.setImageBitmap(qrcodeBitmap);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(item));
    }
    private Bitmap generateQRCode(){
        UserBeasli myUser = ((AuthenticateApplication) getApplicationContext()).getAuth().getUserAuth();
        return QRCode.from(myUser.getId()+"").bitmap();
    }

}
