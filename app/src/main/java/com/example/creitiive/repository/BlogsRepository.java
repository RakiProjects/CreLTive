package com.example.creitiive.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.creitiive.retrofit.RetrofitInstance;
import com.example.creitiive.retrofit.WebApi;
import com.example.creitiive.model.Blog;
import com.example.creitiive.response.BlogsResponse;
import com.example.creitiive.room.database.BlogDatabase;
import com.example.creitiive.room.entity.BlogEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlogsRepository {

    private static final String TAG = BlogsRepository.class.getSimpleName();

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

    public void getBlogList(final Context context, final MutableLiveData<BlogsResponse> blogsLiveData, String token) {

        Call<ArrayList<Blog>> call = service.getBlogList(token);

        call.enqueue(new Callback<ArrayList<Blog>>() {
            @Override
            public void onResponse(Call<ArrayList<Blog>> call, Response<ArrayList<Blog>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Blog> blogList = response.body();

                    //room
                    BlogDatabase db = BlogDatabase.getInstance(context);
                    List<BlogEntity> blogEntityData = db.blogDao().getBlogs();
                    if (blogEntityData.size() == 0) {
                        for (Blog blog : blogList) {
                            BlogEntity blogEntity = new BlogEntity(blog);
                            db.blogDao().insertBlogs(blogEntity);
                        }
                    }
                    //

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
                Log.e(TAG, t.getMessage(), t);
            }
        });

    }


}
