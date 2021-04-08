package com.example.contacts.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.contacts.api.ApiRequestData;
import com.example.contacts.api.RetrofitInstance;
import com.example.contacts.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private MutableLiveData<LoginResponse> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<LoginResponse> postLogindata(String userName,String password) {
       /* ApiRequestData apiService = RetrofitInstance.getRetrofitServer();
        Call<LoginResponse> call = apiService.doLogin(userName,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    LoginResponse loginResponse =  response.body();
                    mutableLiveData.setValue(loginResponse);
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Login Fail",t.getMessage());
            }
        });*/
        return mutableLiveData;
    }
}
