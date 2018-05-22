package com.example.maksym.cinemacalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String MY_LOG = "myLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<MovieInfo> movieListInfo = getIntent().getParcelableArrayListExtra("movie");
        Log.d(MY_LOG, String.valueOf(movieListInfo.size()));

    }

}
