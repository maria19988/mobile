package com.example.user.news.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 3/10/2018.
 */


    public class NewsItem {


        @SerializedName("news")
        private List<NewsDescription> newsDescriptions;

        public List<NewsDescription> getWeatherDescriptions() {
            return newsDescriptions;
        }
    }


