package com.example.user.news.models;

/**
 * Created by user on 3/10/2018.
 */


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NewsInfo {

    @SerializedName("list")
    private List<NewsItem> NewsItems;

    public List<NewsItem> getNewsItems() {
        return NewsItems;
    }
}