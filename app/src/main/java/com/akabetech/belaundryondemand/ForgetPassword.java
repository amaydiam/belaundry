package com.akabetech.belaundryondemand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliAuthenticationAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasliTemp;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrari on 5/23/2017.
 */

public class ForgetPassword extends AppCompatActivity {
    @BindView(R.id.etMailPhone)
    EditText etMailPhone;
    @BindView(R.id.forgetPassword)
    TextView forgetPassword;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMailPhone.getText().equals(""))
                {
                    Toast.makeText(ForgetPassword.this,"Please input your mail or password",Toast.LENGTH_LONG).show();
                }
                else {
                    UserBeasliTemp data = new UserBeasliTemp();
                    data.setEmail(etMailPhone.getText().toString());
                    AdapterComponent
                            .getAdapter(BeAsliAuthenticationAdapter.class).forgetPassword(data).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.code()==200)
                            {
                                Toast.makeText(ForgetPassword.this,"Password baru akan dikirim melalui no yang anda daftarkan silahkan check inbox",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(ForgetPassword.this,"Nomer handphone atau email anda salah",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(ForgetPassword.this,"Tolong Check koneksi internet anda",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
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
