package com.example.creitiive.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.creitiive.RetrofitInstance;
import com.example.creitiive.WebApi;
import com.example.creitiive.model.SingleBlogModel;
import com.example.creitiive.response.SingleBlogResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleBlogRepository {

    private static SingleBlogRepository singleBlogRepository;

    public static SingleBlogRepository getInstance() {
        if (singleBlogRepository == null) {
            singleBlogRepository = new SingleBlogRepository();
        }
        return singleBlogRepository;
    }

    private WebApi service;

    private SingleBlogRepository() {
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getSingleBlog(final MutableLiveData<SingleBlogResponse> singleBlogLiveData, String blogId, String token){
        Call<SingleBlogModel> call = service.getSingleBlog("application/json", token, blogId);

        call.enqueue(new Callback<SingleBlogModel>() {
            @Override
            public void onResponse(Call<SingleBlogModel> call, Response<SingleBlogModel> response) {
                if(response.isSuccessful()){
                    SingleBlogModel blog = response.body();
                    singleBlogLiveData.setValue(new SingleBlogResponse(blog, null));
                }else {
                    try {
                        Log.e("Error ", response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                        singleBlogLiveData.setValue(new SingleBlogResponse(null, e));
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleBlogModel> call, Throwable t) {
                singleBlogLiveData.setValue(new SingleBlogResponse(null, t));
            }
        });
    }
}
