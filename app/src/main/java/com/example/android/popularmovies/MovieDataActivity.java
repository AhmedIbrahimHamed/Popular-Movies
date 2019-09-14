package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class MovieDataActivity extends AppCompatActivity {

    String[] movieData;

    TextView mMovieName,mMovieReleaseData
            ,mMovieOverview,mMovieVoteRate;

    ImageView mMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moive_data);

        mMovieName = (TextView) findViewById(R.id.movie_name_activity);

        mMovieReleaseData = (TextView) findViewById(R.id.movie_year_activity);

        mMovieVoteRate = (TextView) findViewById(R.id.movie_rate_activity);

        mMovieOverview = (TextView) findViewById(R.id.movie_overview_activity);

        mMoviePoster = (ImageView) findViewById(R.id.movie_poster_activity);

        Intent mainActivityIntent = getIntent();

        if(mainActivityIntent.hasExtra(Intent.EXTRA_TEXT))
        {
            String jsonStringData = mainActivityIntent.getStringExtra(Intent.EXTRA_TEXT);
            int moviePos = mainActivityIntent.getIntExtra(Intent.EXTRA_REFERRER,0);

            try {
                JSONArray jsonArrayMovies = JSONParser.getJsonArray(jsonStringData);
                movieData = JSONParser.getMovieData(jsonArrayMovies,moviePos);

                mMovieName.setText(movieData[0]);

                Picasso.with(MovieDataActivity.this)
                        .load("http://image.tmdb.org/t/p/w185/"+movieData[1])
                        .resize(480,640)
                        .into(mMoviePoster);

                mMovieReleaseData.setText("Release year:"+movieData[2]);

                mMovieVoteRate.setText("Vote Average : "+movieData[3]);

                mMovieOverview.setText("Plot Synopsis :"+"\n"+movieData[4]);

            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }
}
