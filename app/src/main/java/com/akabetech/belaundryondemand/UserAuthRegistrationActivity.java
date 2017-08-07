package com.akabetech.belaundryondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.akabetech.belaundryondemand.behavior.activity.FormValidationBehavior;
import com.akabetech.belaundryondemand.util.ValidationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAuthRegistrationActivity extends AppCompatActivity implements FormValidationBehavior {
    @BindView(R.id.arn_et_email)
    EditText etEmail;
    @BindView(R.id.arn_et_password)
    EditText etPassword;
    @BindView(R.id.arn_et_repassword)
    EditText etConfirmPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth_registration);

        ButterKnife.bind(this);
    }


    @Override
    public boolean isValid() {
        boolean result = true;
        if(!ValidationUtil.blankFieldValidator(etEmail,String.format(getString(R.string.error_blank),"email"))){
            result = false;
        }else if(!ValidationUtil.mailFormatValidator(etEmail,String.format(getString(R.string.error_blank),"email"))){
            result = false;
        }

        if(!ValidationUtil.blankFieldValidator(etPassword,String.format(getString(R.string.error_blank),"password"))){
            result = false;
        }
        if(!ValidationUtil.blankFieldValidator(etConfirmPassword,String.format(getString(R.string.error_blank),"confirm password"))){
            result = false;
        }else if(!ValidationUtil.compareValidator(etPassword,etConfirmPassword,String.format(getString(R.string.error_compare_equals),"confirm password","password"))){
            result = false;
        }

        return result;
    }
    @OnClick(R.id.arn_btn_Register)
    @Override
    public void doSubmitForm(View v) {
        if(isValid()){

            Intent intent = new Intent(this,RegistrationActivity.class);
            intent.putExtra(RegistrationActivity.PARAM_EMAIL,etEmail.getText().toString());
            intent.putExtra(RegistrationActivity.PARAM_PASSWORD,etPassword.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    public void reset() {

    }
}
