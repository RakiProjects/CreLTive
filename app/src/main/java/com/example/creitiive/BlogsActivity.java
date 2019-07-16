package com.example.creitiive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.creitiive.adapter.BlogsAdapter;
import com.example.creitiive.model.Blog;
import com.example.creitiive.response.BlogsResponse;
import com.example.creitiive.viewModel.BlogsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BlogsActivity extends AppCompatActivity {


    private BlogsViewModel blogsViewModel;

    private RecyclerView rcView;
    private BlogsAdapter blogsAdapter;

    SharedPreferences sharedPreferences;
    public static final String ID = "blogId";

    public static void start(Context context) {
        Intent starter = new Intent(context, BlogsActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        rcView = (RecyclerView) findViewById(R.id.recycler_view);

        generateBlogsRecyclerView();

        blogsViewModel = ViewModelProviders.of(this).get(BlogsViewModel.class);
        blogsViewModel.blogsLiveData.observe(this, new Observer<BlogsResponse>() {
            @Override
            public void onChanged(BlogsResponse blogsResponse) {
                if (blogsResponse == null) return;
                if (blogsResponse.getThrowable() != null) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "No internet connection! Click to turn it on.", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(R.string.snackbar, new SnackbarListener());
                    snackbar.show();
                } else {
                    blogsAdapter.updateBlogList(blogsResponse.getBlogList());
                }
            }
        });

        blogsViewModel.getBlogList();
    }

    private void generateBlogsRecyclerView() {
        blogsAdapter = new BlogsAdapter(BlogsActivity.this, new ArrayList<Blog>(), new BlogsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Blog item) {

                sharedPreferences = getApplicationContext().getSharedPreferences(ID, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", item.getId().toString());
                editor.commit();

                SingleBlogActivity.start(BlogsActivity.this);
            }
        });
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.setAdapter(blogsAdapter);
    }

    private class SnackbarListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
            startActivity(intent);
        }
    }
}
