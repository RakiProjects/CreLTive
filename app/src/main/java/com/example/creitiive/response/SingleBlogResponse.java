package com.example.creitiive.response;

import com.example.creitiive.model.SingleBlogModel;

public class SingleBlogResponse {

    private SingleBlogModel content;
    private Throwable throwable;

    public SingleBlogResponse(SingleBlogModel content, Throwable throwable) {
        this.content = content;
        this.throwable = throwable;
    }

    public SingleBlogModel getContent() {
        return content;
    }

    public void setContent(SingleBlogModel content) {
        this.content = content;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
