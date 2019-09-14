package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements PopularMoviesAdapter.PMAdapterOnClickHandler {

    private RecyclerView mRecyclerView;

    private PopularMoviesAdapter mAdapter;

    private TextView mErrorText;

    private String jsonStringData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorText = (TextView) findViewById(R.id.error_massage);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new PopularMoviesAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        String url = Network_Util.buildUrlPoster("popular");

        getQueryResult(url);
    }

    @Override
    public void onClick(int moviePos) {
        Context context = MainActivity.this;
        Class destinationActivity = MovieDataActivity.class;

        Intent startMovieDataIntent = new Intent(context,destinationActivity);

        startMovieDataIntent.putExtra(Intent.EXTRA_TEXT,jsonStringData);

        startMovieDataIntent.putExtra(Intent.EXTRA_REFERRER,moviePos);

        startActivity(startMovieDataIntent);

    }

    public void getQueryResult(String queryUrl)
    {
        Ion.with(MainActivity.this)
                .load(queryUrl)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        if(e!=null)
                        {
                            Toast.makeText(MainActivity.this,"Error:" + e.getMessage()
                                    ,Toast.LENGTH_LONG).show();
                        }

                        else
                        {
                            setJsonData(result);
                        }

                    }
                });
    }

    private void setJsonData(String json)
    {
        try {
            jsonStringData=json;
            JSONArray jsonArrayMovies= JSONParser.getJsonArray(json);
            if (jsonArrayMovies!=null)
            {
                String[] posters = JSONParser.getPostersFromJson(jsonArrayMovies);
                showMoviesData();
                mAdapter.setPostersData(MainActivity.this,posters);
            }
            else {
                showErrorMassage();
                mErrorText.setText("There has been a mistake please try later.");
            }

        }catch (JSONException e)
        {
            showErrorMassage();
            mErrorText.setText(e.getMessage());
        }
    }

    private void showErrorMassage()
    {
        mRecyclerView.setVisibility(View.INVISIBLE);

        mErrorText.setVisibility(View.VISIBLE);
    }

    private void showMoviesData()
    {
        mErrorText.setVisibility(View.INVISIBLE);

        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int sortByItemSelected = item.getItemId();
        String qUrl;
        if(sortByItemSelected == R.id.action_sort_popularity)
        {
            qUrl = Network_Util.buildUrlPoster("popular");
            getQueryResult(qUrl);
        }else if(sortByItemSelected == R.id.action_sort_ratings){
            qUrl = Network_Util.buildUrlPoster("top_rated");
            getQueryResult(qUrl);
        }
        return true;
    }

}
