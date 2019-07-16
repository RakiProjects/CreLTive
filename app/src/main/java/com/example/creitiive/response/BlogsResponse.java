package com.example.creitiive.response;

import com.example.creitiive.model.Blog;

import java.util.List;

public class BlogsResponse {

    private List<Blog> blogList;
    private Throwable throwable;

    public BlogsResponse(List<Blog> blogList, Throwable throwable) {
        this.blogList = blogList;
        this.throwable = throwable;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
