package com.example.creitiive.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.creitiive.retrofit.RetrofitInstance;
import com.example.creitiive.retrofit.WebApi;
import com.example.creitiive.model.SingleBlogModel;
import com.example.creitiive.response.SingleBlogResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleBlogRepository {

    private static final String TAG = SingleBlogRepository.class.getSimpleName();

    private static SingleBlogRepository singleBlogRepository;

    private WebApi service;

    private SingleBlogRepository() {
        service = RetrofitInstance.createService(WebApi.class);
    }

    public static SingleBlogRepository getInstance() {
        if (singleBlogRepository == null) {
            singleBlogRepository = new SingleBlogRepository();
        }
        return singleBlogRepository;
    }

    public void getSingleBlog(final MutableLiveData<SingleBlogResponse> singleBlogLiveData, int blogId, String token) {
        Call<SingleBlogModel> call = service.getSingleBlog(token, blogId);

        call.enqueue(new Callback<SingleBlogModel>() {
            @Override
            public void onResponse(Call<SingleBlogModel> call, Response<SingleBlogModel> response) {
                if (response.isSuccessful()) {
                    SingleBlogModel blog = response.body();
                    singleBlogLiveData.setValue(new SingleBlogResponse(blog, null));
                } else {
                    try {
                        Log.e(TAG, response.errorBody().string());

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
