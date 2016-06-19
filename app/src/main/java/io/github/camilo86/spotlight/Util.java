package io.github.camilo86.spotlight;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camilo on 6/19/2016.
 */
public class Util {

    private static String LOG_TAG = Util.class.getSimpleName();

    public static List getPopularPosterURLList(JSONObject jsonObject) {

        JSONArray results;
        List<String> popularPostersURL = new ArrayList<String>();

        try {
            results = jsonObject.getJSONArray("results");

            for(int i = 0; i < results.length(); i++) {
                popularPostersURL.add("http://image.tmdb.org/t/p/w154" + results.getJSONObject(i).getString("poster_path"));
            }

        }catch(Exception e) {
            Log.e(LOG_TAG, "Parsing result's array from movies failed");
        }

        return popularPostersURL;
    }
}
