package com.example.creitiive.retrofit;

import com.example.creitiive.retrofit.HttpInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://blogsdemo.creitiveapps.com/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpInterceptor())
                    .build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return getRetrofitInstance().create(serviceClass);
    }
}
