package com.example.creitiive.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.creitiive.retrofit.RetrofitInstance;
import com.example.creitiive.retrofit.WebApi;
import com.example.creitiive.model.Blog;
import com.example.creitiive.response.BlogsResponse;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlogsRepository {

    private static BlogsRepository blogsRepository;

    public static BlogsRepository getInstance() {
        if (blogsRepository == null) {
            blogsRepository = new BlogsRepository();
        }
        return blogsRepository;
    }

    private WebApi service;

    private BlogsRepository() {
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getBlogList(final MutableLiveData<BlogsResponse> blogsLiveData, String token) {

        Call<ArrayList<Blog>> call = service.getBlogList(token);

        call.enqueue(new Callback<ArrayList<Blog>>() {
            @Override
            public void onResponse(Call<ArrayList<Blog>> call, Response<ArrayList<Blog>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Blog> blogList = response.body();
                    blogsLiveData.setValue(new BlogsResponse(blogList, null));
                } else {
                    try {
                        Log.e("Error ", response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                        blogsLiveData.setValue(new BlogsResponse(null, e));
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Blog>> call, Throwable t) {
                blogsLiveData.setValue(new BlogsResponse(null, t));
                Log.e("t ", t.getMessage(), t);
            }
        });

    }


}
