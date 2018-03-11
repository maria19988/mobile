package com.example.user.news.screens;

import com.airbnb.lottie.LottieAnimationView;
import com.androstock.newsapp.R;
import com.example.user.news.apiManagers.apiManager;
import com.example.user.news.models.NewsInfo;
import com.squareup.picasso.Picasso;;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.SurfaceHolder.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private apiManager ApiManager;
    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView time;
    private LottieAnimationView loadingAnimationView;
    private ListView listNews;
    private ProgressBar loader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNews = (ListView) findViewById(R.id.listNews);
        loader = (ProgressBar) findViewById(R.id.loader);
        listNews.setEmptyView(loader);

        ApiManager = new apiManager();
        /*if(mainfcts.isNetworkAvailable(getApplicationContext()))
        {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }*/

    }

    private void hideLoadingAnimation() {
        loadingAnimationView.cancelAnimation();
        loadingAnimationView.setVisibility(View.GONE);
    }

    private void getWeatherAtCoordinates(double latitude, double longitude) {
        ApiManager = new apiManager();
        ApiManager.getNewsInUs("us").enqueue(new Callback<NewsInfo>() {


            @Override
            public void onResponse(Call<NewsInfo> call, Response<NewsInfo> response) {
                if (response.isSuccessful()) {
                    NewsInfo currentNewsInfo = response.body();
                    if (currentNewsInfo != null) {
                        showCurrentNews(currentNewsInfo);
                        hideLoadingAnimation();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsInfo> call, Throwable t) {
                hideLoadingAnimation();
            }
        });
    }
    private void showCurrentNews(NewsInfo info) {
        String title = String.valueOf(info.getNewsItems().getWeatherDescriptions().getTitle());
        title = String.format(getString(R.string.current_temp), currentTemp);
        currentTemperatureTextView.setText(currentTemp);

        setScreenTitle(info.getCityName());

        if (info.getWeatherDescriptions() != null && !info.getWeatherDescriptions().isEmpty()) {
            WeatherDescription currentWeatherDescription = info.getWeatherDescriptions().get(0);

            String weatherDescription = currentWeatherDescription.getDescription();
            weatherDescriptionTextView.setText(weatherDescription);

            String iconUrl = "http://openweathermap.org/img/w/" + currentWeatherDescription.getIcon() + ".png";
            Picasso.with(this).load(iconUrl).into(currentConditionImageView);
        }

    }


}
