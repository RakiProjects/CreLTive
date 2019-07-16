package com.example.creitiive.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creitiive.R;
import com.example.creitiive.model.Blog;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.BlogsViewHolder>{

    public interface OnItemClickListener{
        void onItemClick(Blog item);
    }

    private ArrayList<Blog> blogs = new ArrayList<>();
    private Context context;
    private final OnItemClickListener listener;

    public BlogsAdapter(Context context, ArrayList<Blog> blogs, OnItemClickListener listener) {
        this.context = context;
        this.blogs = blogs;
        this.listener = listener;
    }


    @NonNull
    @Override
    public BlogsAdapter.BlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_item, parent, false);    // takes as input XML file and builds the View objects from it
        BlogsViewHolder vh = new BlogsViewHolder(view);
        RecyclerView.ViewHolder holder = new BlogsViewHolder(view);//
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BlogsAdapter.BlogsViewHolder holder, int position) {
        holder.blogTitle.setText(blogs.get(position).getTitle());
        String imgURL = blogs.get(position).getImageUrl();
        new DownLoadImageTask(holder.blogImage).execute(imgURL);
        holder.blogDescription.setText(blogs.get(position).getDescription());
        holder.bind(blogs.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }


    public static class BlogsViewHolder extends RecyclerView.ViewHolder{
        TextView blogTitle;
        ImageView blogImage;
        TextView blogDescription;
        TextView blogId;

        public BlogsViewHolder(View itemView){
            super(itemView);
            blogTitle = itemView.findViewById(R.id.blog_title);
            blogImage = itemView.findViewById(R.id.blog_image);
            blogDescription = itemView.findViewById(R.id.blog_description);
        }

        public void bind(final Blog item, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void updateBlogList(List<Blog> list){
        blogs.clear();
        blogs.addAll(list);
        notifyDataSetChanged();
    }

    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}

