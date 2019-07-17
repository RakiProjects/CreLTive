package com.example.creitiive.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.creitiive.model.Params;
import com.example.creitiive.repository.MainActivityRepository;
import com.example.creitiive.response.TokenResponse;

public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<TokenResponse> tokenLiveData = new MutableLiveData<>();
    private MainActivityRepository mainActivityRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void getToken(Params params){
        if(tokenLiveData.getValue() != null){
            return;
        }
        mainActivityRepository = MainActivityRepository.getInstance();
        mainActivityRepository.getToken(getApplication(), tokenLiveData, params);
    }
}
