package com.example.maksym.cinemacalendar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FullMovieActivity extends AppCompatActivity {

    Date fullDate;
    long timaStamp;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            try {
                fullDate = sdf.parse("30/05/2018 22:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            timaStamp = fullDate.getTime();


//            try {
//                fullDate = getDataMovie(dateMovie);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            timaStamp =fullDate.getTime();
//
            AlarmHelper alarmHelper = AlarmHelper.getInstance();
            alarmHelper.setAlarm(nameFullMovie, timaStamp);

        }
    };
    private String urlMovie, nameFullMovie;
    private String MY_LOG = "myLog";
    private TextView infoMovie, rateMovie;
    private Button button;
    private String fullMovieInfo, rateImdbMovie = "", imdbUrl;
    private String dateMovie;
    private ProgressBar progressBar;

    public static Date getDataMovie(String dateMovie) throws ParseException {

//        Date date;
//
//        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
//
//        date = sdf.parse(  dateMovie + " 2018");
//
//        return date;

        Date date;

        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy H mm");

        date = sdf.parse(dateMovie + " 2018 12 00");

        return date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlMovie = getIntent().getStringExtra("movieUrl");
        new ParseFullMovie().execute();

        setContentView(R.layout.activity_full_movie);

        ImageView imageMovie = findViewById(R.id.imageViewFull);
        TextView nameMovie = findViewById(R.id.nameView);
        infoMovie = findViewById(R.id.infoView);
        rateMovie = findViewById(R.id.rateView);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.buttonAdd);
        nameFullMovie = getIntent().getStringExtra("name");
        dateMovie = getIntent().getStringExtra("date");
        nameMovie.setText(getIntent().getStringExtra("name"));

        Picasso.get().load(getIntent().getStringExtra("image")).into(imageMovie);

        new ParseFullMovie().execute();

        AlarmHelper.getInstance().init(getApplicationContext());

        button.setOnClickListener(onClickListener);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("info", fullMovieInfo);
        outState.putString("rate", rateImdbMovie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        infoMovie.setText(savedInstanceState.getString("info"));
        rateMovie.setText(savedInstanceState.getString("rate"));


    }

    class ParseFullMovie extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                Document document = Jsoup.connect(urlMovie).get();

                Elements infoElements = document.select("div[itemscope]");

                fullMovieInfo = infoElements.select("div[id=filmText]").text();
                imdbUrl = ("https:" + infoElements.select("span[class=imdbRatingPlugin]>a").attr("href"));
                Log.d(MY_LOG, imdbUrl);

                Document docImdb = Jsoup.connect(imdbUrl).get();
                Elements imdbElements = docImdb.select("div[class=imdbRating]");
                rateImdbMovie = imdbElements.select("div[class=ratingValue]").text();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            infoMovie.setText(fullMovieInfo);
            if (rateImdbMovie.equals("")) {
                rateMovie.setText("N/A");
            } else {
                rateMovie.setText(rateImdbMovie);
            }

            progressBar.setVisibility(View.INVISIBLE);


        }
    }
}
