package com.example.creitiive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

    SingleBlogViewModel singleBlogViewModel;
    WebView contentView;

    public static void start(Context context) {
        Intent starter = new Intent(context, SingleBlogActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_blog);
        contentView = (WebView)findViewById(R.id.content);
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
        singleBlogViewModel.getSingleBlog();
    }

    private void generateSingleBlog(){
        contentView.loadData("", "text/html", "UTF-8");
    }

    private void updateSingleBlogContent(SingleBlogModel singleBlogContent){
        contentView.loadData(singleBlogContent.getContent(),"text/html", "UTF-8");
    }

//    public String stripHtml(String html) {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
//        } else {
//            return Html.fromHtml(html).toString();
//        }
//    }

}
