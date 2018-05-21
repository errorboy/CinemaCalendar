package com.example.maksym.cinemacalendar;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new ParseMovieTask().execute();
    }

    class ParseMovieTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            ParseMovie();
            return null;
        }
    }

    private void ParseMovie (){
        try {


            String nameEl,dateEl,imgEl,linkEl;

            Document doc = Jsoup.connect("https://kino-teatr.ua/films-near.phtml").get();
            Elements filmElements = doc.select("div[id=content]>" +
                    "div[id=mainContent]>" +
                    "div[id=leftContent]>" +
                    "div[id=news_page]>" +
                    "div[id=searchItemFilms]"); // получаю нужный блоку

            for(Element filmElement : filmElements) {

                nameEl = filmElement.select("div[id=searchItemTitle]>a[href]").text();   // получаем название фильма
                dateEl = filmElement.select("div[id=premiereBigDate]").text();   // получаем дату
                imgEl = filmElement.select("img").attr("src");   // ссылка на картинку
                linkEl = filmElement.select("a").attr("href");   // ссылка на фильм

                movieListInfo.add(new MovieInfo(nameEl,dateEl,imgEl,linkEl));
                Log.d(LOG,nameEl + " " + dateEl + " " + imgEl + " " + linkEl);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
