package com.example.contacts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.contacts.model.LoginResponse;
import com.example.contacts.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    LoginRepository  loginRepository;
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LoginResponse> postLogin(String userName,String password) {
        return loginRepository.postLogindata(userName,password);
    }
}
