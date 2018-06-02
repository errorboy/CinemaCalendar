package com.example.maksym.cinemacalendar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogoActivity extends AppCompatActivity {

    private static final String LOG = "LOG";
    private List<MovieInfo> movieListInfo = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        progressBar = findViewById(R.id.progressBar);


        new ParseMovieTask().execute();


    }


    private void ParseMovie (){
        ProgressBar logoBar = findViewById(R.id.logoBar);

        try {

            int count = 0;
            String nameEl, dateEl, imgEl, linkEl, allInfoEl, infoEl, actorsEl, rateEl;

            Document doc = Jsoup.connect("https://kino-teatr.ua/films-near.phtml").get();
            Elements filmElements = doc.select("div[id=content]>" +
                    "div[id=mainContent]>" +
                    "div[id=leftContent]>" +
                    "div[id=news_page]>" +
                    "div[id=searchItemFilms]"); // получаю нужный блок

//            Log.d(LOG, String.valueOf(filmElements.size()));
            logoBar.setMax(filmElements.size());

//            Log.d(LOG, String.valueOf(logoBar.getMax()));

            for(Element filmElement : filmElements) {

                nameEl = filmElement.select("div[id=searchItemTitle]>a[href]").text(); // получаем название фильма
                dateEl = filmElement.select("div[id=premiereBigDate]").text(); // получаем дату
                imgEl = filmElement.select("img").attr("src");
                linkEl = filmElement.select("a").attr("href");
                allInfoEl = filmElement.select("p[class=searchItemText]").text();
                infoEl = ("201" + allInfoEl.replaceAll(".* 201", "").replaceAll(" Режиссеры.*", "")); //информация о фильме
                actorsEl = ("Актеры:" + allInfoEl.replaceAll(".* Актеры:", "").replaceAll("    Этот.*", "")); //информация о фильме
                rateEl = allInfoEl.split(" 201", 2)[0]; // рейтинг

                movieListInfo.add(new MovieInfo(nameEl, rateEl, dateEl, imgEl, linkEl, infoEl, actorsEl));
//                Log.d(LOG, nameEl + " " + dateEl + " " + imgEl + " " + linkEl + " " + infoEl + " " + actorsEl + " " + rateEl);

                logoBar.setProgress(count);

//                Log.d(LOG, String.valueOf(count));
                count++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    class ParseMovieTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ParseMovie();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(LogoActivity.this, MainActivity.class);
            intent.putParcelableArrayListExtra("movie", (ArrayList<? extends Parcelable>) movieListInfo);
            startActivity(intent);
            finish();
        }
    }

}
