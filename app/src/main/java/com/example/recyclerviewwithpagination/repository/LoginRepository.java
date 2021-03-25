package com.example.recyclerviewwithpagination.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.recyclerviewwithpagination.api.ApiRequestData;
import com.example.recyclerviewwithpagination.api.RetroServer;
import com.example.recyclerviewwithpagination.model.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository {
    private MutableLiveData<LoginResponse> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<LoginResponse> postLogindata(String userName,String password) {
        ApiRequestData apiService = RetroServer.getRetrofitServer();
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
        });
        return mutableLiveData;
    }
}
