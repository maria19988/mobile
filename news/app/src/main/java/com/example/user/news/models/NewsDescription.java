package com.example.user.news.models;

/**
 * Created by user on 3/11/2018.
 */

public class NewsDescription
{
    private String title;
    private String Description;
    private String url;
    private String urlToImage;
    private String publishedAt;


    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return Description;
    }
    public String getUrl() {return url;}
    public String getUrlToImage() {return urlToImage;}
    public String getTime() {return publishedAt;}
}
