package com.example.creitiive.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.creitiive.model.Blog;

@Entity(tableName = "blogs")
public class BlogEntity {

    @PrimaryKey(autoGenerate = false)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "image_src")
    public String image_src;

    @ColumnInfo(name = "description")
    public String description;

    public BlogEntity() {
    }

    public BlogEntity(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        // slika treba da se skine u drawable
        this.image_src = blog.getImageUrl();
        this.description = blog.getDescription();
    }
}
