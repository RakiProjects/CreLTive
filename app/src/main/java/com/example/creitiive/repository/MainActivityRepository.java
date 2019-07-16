package com.example.creitiive.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.creitiive.BlogsActivity;
import com.example.creitiive.MainActivity;
import com.example.creitiive.retrofit.RetrofitInstance;
import com.example.creitiive.retrofit.WebApi;
import com.example.creitiive.model.Params;
import com.example.creitiive.model.Token;
import com.example.creitiive.response.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MainActivityRepository {

    private static MainActivityRepository mainActivityRepository;
    SharedPreferences sharedPreferences;

    public static MainActivityRepository getInstance() {
        if (mainActivityRepository == null) {
            mainActivityRepository = new MainActivityRepository();
        }
        return mainActivityRepository;
    }

    private WebApi service;

    private MainActivityRepository() {
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getToken(final MutableLiveData tokenLiveData, Params params, final Context context) {

        Call<Token> call = service.getToken(params);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token token = response.body();
                    String t = token.getToken();

                    Log.v("token", t);
                    sharedPreferences = context.getApplicationContext().getSharedPreferences(MainActivity.TOKEN, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", t);
                    editor.commit();
                    BlogsActivity.start(context);
                } else {
                    if(response.code() == 400){
                        Toast.makeText(context, "Please fill the form", Toast.LENGTH_LONG).show();
                    }
                    if(response.code() == 401){
                        tokenLiveData.setValue(new TokenResponse(null, 401, null));
                        Toast.makeText(context, "Wrong email or password", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                tokenLiveData.setValue(new TokenResponse(null, 0, t));
            }
        });
    }
}