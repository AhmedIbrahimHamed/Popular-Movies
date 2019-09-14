package com.example.android.popularmovies;

import android.net.Uri;

/**
 * Created by WinDows8 on 3/5/2017.
 */

public final class Network_Util {

    private static String response = null;

    private static final String scheme = "https";
    private static final String autho = "api.themoviedb.org";
    private static final String path1 = "3";
    private static final String path2 = "movie";
    //Please put ur own key in the api variable
    private static final String api = "";
    private static final String lang = "en-US";
    private static final String page = "1";


    private static final String API_KEY_QUERY = "api_key";
    private static final String LANG_Query = "language";
    private static final String PAGE_QUERY = "page";

    /**
     * Builds Url for Querying movies sorted by most popular
     * or top rated from TMDb api.
     * @param path3 String which is popular that get popular movies info or
     *              top_rated that returns top_rated movies info.
     * @return The url that will be used to query the movies.
     */

    public static String buildUrlPoster(String path3)
    {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .authority(autho)
                .appendPath(path1)
                .appendPath(path2)
                .appendPath(path3)
                .appendQueryParameter(API_KEY_QUERY,api)
                .appendQueryParameter(LANG_Query,lang)
                .appendQueryParameter(PAGE_QUERY,page);

        Uri addressUri = builder.build();

        String postersUrl = addressUri.toString();

        return postersUrl;

    }


}




