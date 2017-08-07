package com.akabetech.belaundryondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliAuthenticationAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasliTemp;
import com.akabetech.belaundryondemand.retrofit.model.request.LoginRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrari on 5/20/2017.
 */

public class VerificationCode extends AppCompatActivity {
    public static UserBeasliTemp instance;

    public static UserBeasliTemp getInstance() {
        return instance;
    }

    public static void setInstance(UserBeasliTemp instance) {
        VerificationCode.instance = instance;
    }

    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.et5)
    EditText et5;
    @BindView(R.id.et6)
    EditText et6;
    @BindView(R.id.resendCode)
    TextView resendCode;
    @BindView(R.id.btnVerify)
    TextView btnVerify;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_register);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        getAction();
    }

    private void getAction() {
        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserBeasliTemp var = getInstance();
                AdapterComponent
                        .getAdapter(BeAsliAuthenticationAdapter.class)
                        .resendCodeConfirmasi(var).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code()==200)
                        {
                            Toast.makeText(VerificationCode.this,"code confirmasi akan dikirim melalui no yang anda daftarkan silahkan check inbox",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(VerificationCode.this,response.body(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(VerificationCode.this,"Please check your connection internet",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeVerify=et1.getText().toString()+et2.getText().toString()+et3.getText().toString()+et4.getText().toString()+et5.getText().toString()+et6.getText().toString();
                postData(codeVerify);
            }
        });
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et1.getText().toString().length()==1)
                {
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et2.getText().toString().length()==1)
                {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et3.getText().toString().length()==1)
                {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et4.getText().toString().length()==1)
                {
                    et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et5.getText().toString().length()==1)
                {
                    et6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et1.getText().toString().length()==1)
                {
                    btnVerify.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void postData(String codeVerify) {
        final UserBeasliTemp var = getInstance();
        var.setCon_code(codeVerify);
        AdapterComponent
                .getAdapter(BeAsliAuthenticationAdapter.class)
                .loginWithVerify(var)
                .enqueue(new Callback<UserBeasli>() {
                    @Override
                    public void onResponse(Call<UserBeasli> call, Response<UserBeasli> response) {
                        if(response.code()==200)
                        {
                            ((AuthenticateApplication) getApplication()).getAuth().doLogin(response.body().getEmail(), var.getPassword());
                            ((AuthenticateApplication) getApplication()).getAuth().setUserAuth(response.body());
                            startActivity(new Intent(VerificationCode.this, MainMenu.class));
                            finish();
                        }
                        else {
                            Toast.makeText(VerificationCode.this, response.body().getStatus(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserBeasli> call, Throwable t) {

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
