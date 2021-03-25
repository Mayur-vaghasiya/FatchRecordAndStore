package com.example.recyclerviewwithpagination.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recyclerviewwithpagination.model.LoginResponse;
import com.example.recyclerviewwithpagination.repository.LoginRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    LoginRepository  loginRepository;
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LoginResponse> postLogin(String userName,String password) {
        return loginRepository.postLogindata(userName,password);
    }
}
