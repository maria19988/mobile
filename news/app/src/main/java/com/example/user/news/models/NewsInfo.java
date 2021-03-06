package com.example.user.news.models;

/**
 * Created by user on 3/10/2018.
 */


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class NewsInfo implements Serializable {

    @SerializedName("totalResults")
    private int result;
    @SerializedName("articles")
    private List<NewsItem> items;

    public int getResult() {return result;}
    public List<NewsItem> getItems() {return items;}
}