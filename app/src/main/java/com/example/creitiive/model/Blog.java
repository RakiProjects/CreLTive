package com.example.creitiive.model;

import com.example.creitiive.room.entity.BlogEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Blog {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("description")
    @Expose
    private String description;

    // iz Entity u Model


    public Blog(BlogEntity blogEntity) {
        this.id = blogEntity.id;
        this.title = blogEntity.title;
        this.imageUrl = blogEntity.image_src;
        this.description = blogEntity.description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
