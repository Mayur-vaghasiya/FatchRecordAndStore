package com.example.recyclerviewwithpagination.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.recyclerviewwithpagination.R;
import com.example.recyclerviewwithpagination.custom_views.CustomProgressDialog;
import com.example.recyclerviewwithpagination.model.LoginResponse;
import com.example.recyclerviewwithpagination.viewmodel.LoginViewModel;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity = null;
    private static final String TAG = "LoginActivity";
    private Toolbar toolbar;
    private CustomProgressDialog progress = null;

   private AppCompatEditText edittextEmail,edittextPassword;
   private AppCompatTextView textviewLogin;
   private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AppCompatTextView txtHeaderNname = (AppCompatTextView) toolbar.findViewById(R.id.actv_header_name);
        txtHeaderNname.setText(getString(R.string.login));
        edittextEmail = findViewById(R.id.edittextEmail);
        edittextPassword = findViewById(R.id.edittextPassword);
        textviewLogin=findViewById(R.id.textviewLogin);

        textviewLogin.setOnClickListener(this);

       loginViewModel  = ViewModelProviders.of(this).get(LoginViewModel.class);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textviewLogin:
                loginViewModel.postLogin(edittextEmail.getText().toString(),edittextPassword.getText().toString()).observe(this, new Observer<LoginResponse>() {
                    @Override
                    public void onChanged(LoginResponse loginResponse) {
                        startActivity(new Intent(activity, TodoActivity.class));
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    }
                });
                break;
        }
    }
}