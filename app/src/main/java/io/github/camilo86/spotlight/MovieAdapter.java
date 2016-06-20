package io.github.camilo86.spotlight;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Camilo Gonzalez on 6/17/2016.
 */
public class MovieAdapter extends BaseAdapter {

    private final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private List<String> urls = new ArrayList<String>();
    private Context mContext;

    public MovieAdapter(Context context, List urls) {
        this.mContext = context;

        this.urls = urls;

    }

    @Override
    public int getCount() {
        return this.urls.size();
    }

    @Override
    public String getItem(int i) {
        return this.urls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(mContext);
        }

        String url = getItem(position);

        // external library
        Picasso.with(mContext) //
                .load(url) //
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.error) //
                .fit() //
                .tag(mContext) //
                .into(view);

        return view;
    }
}
