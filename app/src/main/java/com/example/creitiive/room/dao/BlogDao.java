package com.example.creitiive.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.creitiive.model.Blog;
import com.example.creitiive.room.entity.BlogEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface BlogDao {

    @Query("SELECT * FROM blogs")
    List<BlogEntity> getBlogs();

    @Insert
    void insertBlogs(BlogEntity... blogs);
}
