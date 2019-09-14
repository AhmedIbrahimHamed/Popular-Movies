package com.example.android.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ahmed on 3/8/2017.
 */

public final class JSONParser {
    private static final String RESULT_ARRAY = "results";

    private static final String POSTER_PATH = "poster_path";

    private static final String STATUS_MASSAGE = "status_message";

    private static final String ORIGINAL_TITLE = "original_title";

    private static final String OVER_VIEW = "overview";

    private static final String RELEASE_DATA = "release_date";

    private static final String VOTE_AVERAGE = "vote_average";



    public static JSONArray getJsonArray(String movieJson) throws JSONException
    {
        JSONObject moviesJsonData = new JSONObject(movieJson);

        if(moviesJsonData.has("STATUS_MASSAGE"))
        {
            throw new JSONException(moviesJsonData.getString(STATUS_MASSAGE));
        }

        JSONArray moviesArrayData = moviesJsonData.getJSONArray(RESULT_ARRAY);

        return moviesArrayData;
    }


    public static String[] getPostersFromJson(JSONArray moviesArrayData) throws JSONException
    {

        String[] parsedPostersData;

        parsedPostersData = new String[moviesArrayData.length()];

        JSONObject movie;

        for (int i=0;i<moviesArrayData.length();i++)
        {
            movie = moviesArrayData.getJSONObject(i);
            parsedPostersData[i] = movie.getString(POSTER_PATH);
        }

        return parsedPostersData;
    }

    public static String[] getMovieData(JSONArray moviesArrayData,int pos) throws JSONException
    {
        String[] movieData= new String[5];

        JSONObject jsonMovieData ;
        jsonMovieData= moviesArrayData.getJSONObject(pos);

        movieData[0] = jsonMovieData.getString(ORIGINAL_TITLE);
        movieData[1] = jsonMovieData.getString(POSTER_PATH);
        movieData[2] = jsonMovieData.getString(RELEASE_DATA);
        movieData[3] = jsonMovieData.getString(VOTE_AVERAGE);
        movieData[4] = jsonMovieData.getString(OVER_VIEW);

        return movieData;
    }


}
