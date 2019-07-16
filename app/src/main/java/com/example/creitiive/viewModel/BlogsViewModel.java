package com.example.creitiive.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.creitiive.MainActivity;
import com.example.creitiive.repository.BlogsRepository;
import com.example.creitiive.response.BlogsResponse;

public class BlogsViewModel  extends AndroidViewModel {

    public MutableLiveData<BlogsResponse> blogsLiveData = new MutableLiveData<>();
    private BlogsRepository blogsRepository;

    public BlogsViewModel(@NonNull Application application) {
        super(application);
    }

    public void getBlogList(){
        if(blogsLiveData.getValue() != null){
            return;
        }
        blogsRepository = BlogsRepository.getInstance();
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(MainActivity.TOKEN, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        blogsRepository.getBlogList(blogsLiveData, token);
    }
}
