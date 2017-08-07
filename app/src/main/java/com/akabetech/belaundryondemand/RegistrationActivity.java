package com.akabetech.belaundryondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.akabetech.belaundryondemand.behavior.activity.FormValidationBehavior;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliUserAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasliTemp;
import com.akabetech.belaundryondemand.util.ValidationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements FormValidationBehavior{

    //params
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    @BindView(R.id.arn_et_email)
    EditText etEmail;
    @BindView(R.id.arn_et_password)
    EditText etPassword;
    @BindView(R.id.arn_et_repassword)
    EditText etConfirmPassword;
    @BindView(R.id.arn_et_name)
    EditText etName;
    @BindView(R.id.arn_et_phone)
    EditText etPhone;
    @BindView(R.id.arn_et_address)
    EditText etAddress;
//    @BindView(R.id.etCityRegister)
//    EditText etCity;
//    @BindView(R.id.etPostalRegister)
//    EditText etPostal;
    UserBeasli userBeasli = new UserBeasli();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_new);
        ButterKnife.bind(this);
//        loadDataFromPreviousPage();
    }

    private void loadDataFromPreviousPage(){
        userBeasli.setEmail(getIntent().getExtras().getString(PARAM_EMAIL));
        userBeasli.setPassword(getIntent().getExtras().getString(PARAM_PASSWORD));
    }


    @Override
    public boolean isValid() {
       boolean valid = true;
        if(!ValidationUtil.blankFieldValidator(etEmail,String.format(getString(R.string.error_blank),"email"))){
            valid = false;
        }else if(!ValidationUtil.mailFormatValidator(etEmail,String.format(getString(R.string.error_blank),"email"))){
            valid = false;
        }

        if(!ValidationUtil.blankFieldValidator(etPassword,String.format(getString(R.string.error_blank),"password"))){
            valid = false;
        }
        if(!ValidationUtil.blankFieldValidator(etConfirmPassword,String.format(getString(R.string.error_blank),"confirm password"))){
            valid = false;
        }else if(!ValidationUtil.compareValidator(etPassword,etConfirmPassword,String.format(getString(R.string.error_compare_equals),"confirm password","password"))){
            valid = false;
        }
        if(!ValidationUtil.blankFieldValidator(etName,String.format(getString(R.string.error_blank),"name"))){
            valid = false;
        }
        if(!ValidationUtil.blankFieldValidator(etAddress,String.format(getString(R.string.error_blank),"address"))){
            valid = false;
        }
        return valid;
    }

    @Override
    @OnClick(R.id.arn_btn_Register)
    public void doSubmitForm(View v) {
        if(isValid()){
            doPostData();

        }
    }

    @OnClick(R.id.arn_btn_login)
    public void gotoLogin(View v)
    {
        startActivity(new Intent(this, LoginActivity.class));
    }
    @Override
    public void reset() {
//        etPostal.setText("");
//        etCity.setText("");
        etName.setText("");
//        etAddress.setText("");
        etPhone.setText("");
    }

    private void doPostData(){
        userBeasli.setEmail(etEmail.getText().toString());
        userBeasli.setPassword(etPassword.getText().toString());
        userBeasli.setName(etName.getText().toString());
        userBeasli.setAddress(etAddress.getText().toString());
        userBeasli.setPhoneNumber(etPhone.getText().toString());
        AdapterComponent.getAdapter(BeAsliUserAdapter.class).add(userBeasli).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200){
                    if(response.body().equalsIgnoreCase("please konfirmasi code")){
                        Toast.makeText(RegistrationActivity.this,"your registration success",Toast.LENGTH_LONG).show();
                        UserBeasliTemp data=new UserBeasliTemp();
                        data.setEmail(userBeasli.getEmail());
                        data.setPassword(userBeasli.getPassword());
                        VerificationCode.setInstance(data);
                        startActivity(new Intent(RegistrationActivity.this,VerificationCode.class));

                    }
                    else{
                        Toast.makeText(RegistrationActivity.this,response.code()+"-"+response.body(),Toast.LENGTH_LONG).show();
                    }
                }if(response.code()==201){
                    Toast.makeText(RegistrationActivity.this,"your registration success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegistrationActivity.this,VerificationCode.class));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }
}
