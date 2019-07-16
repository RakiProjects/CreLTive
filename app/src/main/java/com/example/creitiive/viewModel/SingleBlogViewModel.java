package com.example.creitiive.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.creitiive.BlogsActivity;
import com.example.creitiive.MainActivity;
import com.example.creitiive.repository.SingleBlogRepository;
import com.example.creitiive.response.SingleBlogResponse;

public class SingleBlogViewModel extends AndroidViewModel {

    public MutableLiveData<SingleBlogResponse> singleBlogLiveData = new MutableLiveData<>();
    private SingleBlogRepository singleBlogRepository;

    public SingleBlogViewModel(@NonNull Application application) {
        super(application);
    }

    public void getSingleBlog(int blogId){
        if(singleBlogLiveData.getValue() != null){
            return;
        }
        singleBlogRepository = SingleBlogRepository.getInstance();
        SharedPreferences preferences = getApplication().getSharedPreferences(MainActivity.TOKEN, Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        singleBlogRepository.getSingleBlog(singleBlogLiveData, blogId, token);
    }

}
