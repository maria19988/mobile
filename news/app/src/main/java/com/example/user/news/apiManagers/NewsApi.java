package com.example.user.news.apiManagers;

import com.example.user.news.models.NewsInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("top-headlines")
    Call<NewsInfo> getNews(@Query("sources") String source);


    //@GET("weather")
    //Call<CategoryNewsInfo> getWeatherByCategory(@Query("category") String cat);

}
