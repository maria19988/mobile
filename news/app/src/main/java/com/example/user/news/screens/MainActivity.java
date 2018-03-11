package com.example.user.news.screens;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.user.news.R;
import com.example.user.news.apiManagers.apiManager;
import com.example.user.news.models.NewsInfo;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Response;

;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    public final int PERMISSION_REQUEST_CODE = 100;
    private apiManager ApiManager;
    private ImageView final_image;
    private TextView final_title;
    private TextView final_description;
    private TextView final_time;
    private LottieAnimationView loadingAnimationView;
    private ListView listNews;
    private ProgressBar loader;
    private String country;
    private Call<NewsInfo> info;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            country = "us";

            getNewsInUs(country);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
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
    private void getNewsInUs(String string) {
        ApiManager.getNewsInUs(country).enqueue(new retrofit2.Callback<NewsInfo>() {
            @Override
            public void onResponse(Call<NewsInfo> call, Response<NewsInfo> response) {
                if (response.isSuccessful()) {
                    NewsInfo newsInfo = response.body();
                    Log.d(TAG, "we are going to show the news");
                    if (newsInfo != null) {
                        for(int i=0; i<newsInfo.getResult(); i++){
                        showCurrentNews(newsInfo);}
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

        Log.d(TAG, "we are going to show the news");
        if (info.getItems() != null) {
        if (info.getItems().getTitle() != null && !info.getItems().getTitle().isEmpty()) {
            String title = (String) info.getItems().getTitle();

            final_title.setText(title);}
            if (info.getItems().getDescription() != null && !info.getItems().getDescription().isEmpty()) {
                String description = (String) info.getItems().getDescription();

                final_description.setText(description);
            }
            if (info.getItems().getTime() != null && !info.getItems().getTime().isEmpty()) {
                String time = (String) info.getItems().getTime();

                final_time.setText(time);
            }
            if (info.getItems().getUrlToImage() != null && !info.getItems().getUrlToImage().isEmpty()) {
                String iconUrl = (String) info.getItems().getUrlToImage();
                Picasso.with(this).load(iconUrl).into(final_image);
            }
        }
    }




}
