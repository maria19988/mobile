package com.example.user.news.apiManagers;
/**
 * Created by user on 3/10/2018.
 */
import com.example.user.news.models.NewsInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class apiManager
{
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private NewsApi newsApi;

    public static final String NEWS_BASE_URL = "https://newsapi.org/v2/";

    public apiManager() {
        Gson gson = new GsonBuilder().create();
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new OpenNewsInterceptor())
                .build();
        retrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        newsApi = retrofit.create(NewsApi.class);
    }



    public Call<NewsInfo> getNews(String source) {
        return newsApi.getNews(source);
    }

    /*public Call<CategoryNewsInfo> getWeatherByCategory(String cat) {
        return weatherApi.getWeatherByCategory(cat);
    }*/

    private static class OpenNewsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl originalUrl = request.url();
            HttpUrl modifiedUrl = originalUrl
                    .newBuilder()
                    .addQueryParameter("apiKey", "557d1bc7c9cc4aefbdf739b035a6c0f0")
                    .build();
            Request modifiedRequest = request.newBuilder().url(modifiedUrl).build();
            return chain.proceed(modifiedRequest);
        }
    }

}
