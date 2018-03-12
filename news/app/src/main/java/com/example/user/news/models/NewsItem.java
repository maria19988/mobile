package com.example.user.news.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/10/2018.
 */


    public class NewsItem {
    @SerializedName("title")
    private String T;
    @SerializedName("description")
    private String Description;
    @SerializedName("url")
    private String URL;
    @SerializedName("urlToImage")
    private String image;
    @SerializedName("publishedAt")
    private String time;


    public String getTitle() {return T;}
    public String getDescription() {
        return Description;
    }
    public String getUrl() {return URL;}
    public String getUrlToImage() {return image;}
    public String getTime() {return time;}


}


