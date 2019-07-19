package com.example.creitiive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.creitiive.model.SingleBlogModel;
import com.example.creitiive.response.SingleBlogResponse;
import com.example.creitiive.viewModel.SingleBlogViewModel;

public class SingleBlogActivity extends AppCompatActivity {

    private static final String EXTRA_BLOG_ID = "EXTRA_BLOG_ID";

    SingleBlogViewModel singleBlogViewModel;
    WebView contentView;

    String baseUrl;
    String mimeType;
    String encoding;
    String historyUrl;

    public static void start(Context context, int blogId) {
        Intent starter = new Intent(context, SingleBlogActivity.class);
        starter.putExtra(EXTRA_BLOG_ID, blogId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_blog);

        baseUrl = "http://blogsdemo.creitiveapps.com";
        mimeType = "text/html";
        encoding = "UTF-8";
        historyUrl = "http://blogsdemo.creitiveapps.com/blogs";

        contentView = findViewById(R.id.web_content);
        contentView.getSettings().setJavaScriptEnabled(true);

        generateSingleBlog();

        singleBlogViewModel = ViewModelProviders.of(this).get(SingleBlogViewModel.class);
        singleBlogViewModel.singleBlogLiveData.observe(this, new Observer<SingleBlogResponse>() {
            @Override
            public void onChanged(SingleBlogResponse singleBlogResponse) {
                if(singleBlogResponse == null) return;
                if(singleBlogResponse.getThrowable() !=null){
                    // kada ima greska
                }
                updateSingleBlogContent(singleBlogResponse.getContent());
            }
        });
        Intent intent = getIntent();
        int blogId = intent.getIntExtra(EXTRA_BLOG_ID, 0);
        singleBlogViewModel.getSingleBlog(blogId);
    }

    private void generateSingleBlog(){
        contentView.loadDataWithBaseURL(baseUrl, "",mimeType, encoding, historyUrl);
    }

    private void updateSingleBlogContent(SingleBlogModel singleBlogContent){
        contentView.loadDataWithBaseURL(baseUrl, singleBlogContent.getContent(),mimeType, encoding, historyUrl);
    }
}
