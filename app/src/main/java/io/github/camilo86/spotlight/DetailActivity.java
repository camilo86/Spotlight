package io.github.camilo86.spotlight;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private JSONObject currentMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            try {
                this.currentMovieData = new JSONObject(intent.getStringExtra(Intent.EXTRA_TEXT));
                this.setTitle(this.currentMovieData.getString("original_title"));

                TextView overView = (TextView) findViewById(R.id.overview);
                overView.setText(this.currentMovieData.getString("overview"));

                TextView rating = (TextView) findViewById(R.id.rating);
                rating.setText("Rating: " + this.currentMovieData.getDouble("vote_average"));

                TextView release = (TextView) findViewById(R.id.release);
                release.setText("Release: " + this.currentMovieData.getString("release_date"));

                ImageView poster = (ImageView) findViewById(R.id.poster);
                Picasso.with(this) //
                        .load("http://image.tmdb.org/t/p/w300" + this.currentMovieData.getString("backdrop_path"))
                        .placeholder(R.drawable.placeholder) //
                        .error(R.drawable.error) //
                        .fit() //
                        .tag(this) //
                        .into(poster);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
