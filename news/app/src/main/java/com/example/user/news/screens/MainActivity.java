package com.example.user.news.screens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.news.R;
import com.example.user.news.apiManagers.apiManager;
import com.example.user.news.models.NewsInfo;
import com.example.user.news.models.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public final int PERMISSION_REQUEST_CODE = 100;
    private final int CITY_ACTIVITY_REQUEST_CODE = 200;

    private apiManager ApiManager;
    private static final String TAG = "MyActivity";
    private Spinner sources;
    private Button search;
    private String SOURCE, source;
    private ArrayAdapter<CharSequence> sourcesAdapter;
    private ImageView currentImageView;
    private TextView currentTitle, currentDescription;
    private TextView time;
    private RecyclerView newsRecyclerView;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sources = findViewById(R.id.source);

        List<String> spinnerArray =  new ArrayList<String>();


        sourcesAdapter = ArrayAdapter.createFromResource(this, R.array.news_sources, android.R.layout.simple_spinner_item);

        sources.setAdapter(sourcesAdapter);

        ApiManager = new apiManager();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int source = sources.getSelectedItemPosition();

                Context context = getApplicationContext();

                Toast toast = Toast.makeText(context, source + " ", Toast.LENGTH_SHORT);
                toast.show();

                if (source == 0)
                    SOURCE = "abc-news";

                if (source == 1)
                    SOURCE = "bbc-news";

                if (source == 2)
                    SOURCE = "cnn";

                if (source == 3)
                    SOURCE = "espn";

                if (source == 4)
                    SOURCE = "mtv-news-uk";


                getNews(SOURCE);

                search.setVisibility(View.GONE);
                sources.setVisibility(View.GONE);
            }
        };

        search = findViewById(R.id.search);
        search.setOnClickListener(listener);

    }

    private void getNews(String sources) {
        ApiManager.getNews(sources).enqueue(new Callback<NewsInfo>() {
            @Override
            public void onResponse(Call<NewsInfo> call, Response<NewsInfo> response) {
                if(response.isSuccessful()){
                    NewsInfo newsInfo=response.body();
                    if (newsInfo != null){
                        Log.d(TAG, "hello");
                        showCurrentNews(newsInfo);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsInfo> call, Throwable t) {

            }
        });
    }
    private void showCurrentNews(NewsInfo newsInfo)
    {
        newsRecyclerView = findViewById(R.id.list_source);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<NewsItem> items = newsInfo.getItems();
        NewsListAdapter adapter=new NewsListAdapter(items);
        newsRecyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.searching:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

        private List<NewsItem> items;
        private NewsInfo info;

        public NewsListAdapter(List<NewsItem> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Context context = holder.itemView.getContext();
            String title = items.get(position).getTitle();
            holder.currentTitle.setText(title);

            String description = items.get(position).getDescription();
            holder.descriptionTextView.setText(description);

            String time = items.get(position).getTime();
            holder.timeTextView.setText(time);

            String iconUrl = items.get(position).getUrlToImage();
            Picasso.with(context).load(iconUrl).into(holder.currentImageView);


        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            ImageView currentImageView;
            TextView currentTitle;
            TextView descriptionTextView;
            TextView timeTextView;
            TextView urlTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                currentImageView = itemView.findViewById(R.id.imageView);
                currentTitle = itemView.findViewById(R.id.title);
                descriptionTextView = itemView.findViewById(R.id.desc);
                timeTextView = itemView.findViewById(R.id.time);
               // urlTextView = itemView.findViewById(R.id.url);
                //urlTextView.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }

    }
}
