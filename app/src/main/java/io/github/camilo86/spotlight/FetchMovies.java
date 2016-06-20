package io.github.camilo86.spotlight;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMovies extends AsyncTask<String, Void, JSONObject> {

    private final String LOG_TAG = FetchMovies.class.getSimpleName();
    private boolean sortByPopularity;
    private String FORECAST_BASE_URL;
    private static String KEY = "---YOUR-THEMOVIEDB-KEY-HERE---";

    public FetchMovies(boolean sortByPopularity) {
        this.sortByPopularity = sortByPopularity;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson = null;

        try {

            if(this.sortByPopularity) {
                FORECAST_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
            }else {
                FORECAST_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated";
            }

            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY, this.KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            moviesJson = buffer.toString();

            Log.v(LOG_TAG, "Movies listing string: " + moviesJson);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return new JSONObject(moviesJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}