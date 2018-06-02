package com.example.maksym.cinemacalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private String MY_LOG = "myLog";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        ArrayList<MovieInfo> movieListInfo = getIntent().getParcelableArrayListExtra("movie"); //получает массив фильмов

//        Log.d(MY_LOG, String.valueOf(movieListInfo.size()));


        recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        recyclerView.setHasFixedSize(true);


        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(movieListInfo);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder> {

        List<MovieInfo> movieIfs;
        private final View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
//                Log.d(MY_LOG, String.valueOf(itemPosition));
                Intent intentMovieUrl = new Intent(MainActivity.this, FullMovieActivity.class);
                intentMovieUrl.putExtra("movieUrl", movieIfs.get(itemPosition).getUrl());
                intentMovieUrl.putExtra("name", movieIfs.get(itemPosition).getName());
                intentMovieUrl.putExtra("image", movieIfs.get(itemPosition).getImgUrl());
                intentMovieUrl.putExtra("date", movieIfs.get(itemPosition).getDate());
                startActivity(intentMovieUrl);
            }
        };

        public RecyclerViewAdapter(List<MovieInfo> movieIfs) {
            this.movieIfs = movieIfs;
        }

        @NonNull
        @Override
        public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_movie, parent, false);
            MovieViewHolder movieViewHolder = new MovieViewHolder(view);
            view.setOnClickListener(onClickListener);
            return movieViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

            holder.viewName.setText(movieIfs.get(position).getName());
            holder.viewRate.setText(movieIfs.get(position).getRate());
            holder.viewInfo.setText(movieIfs.get(position).getInfo());
            holder.viewDate.setText(movieIfs.get(position).getDate());
            holder.viewActors.setText(movieIfs.get(position).getActors());
            Picasso.get().load(movieIfs.get(position).getImgUrl()).into(holder.viewImage);

        }

        @Override
        public int getItemCount() {

            return movieIfs.size();
        }

        public class MovieViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            ImageView viewImage;
            TextView viewName, viewRate, viewInfo, viewActors, viewDate;

            public MovieViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardView);
                viewActors = itemView.findViewById(R.id.actorsView);
                viewName = itemView.findViewById(R.id.nameView);
                viewRate = itemView.findViewById(R.id.rateView);
                viewInfo = itemView.findViewById(R.id.infoView);
                viewDate = itemView.findViewById(R.id.dateView);
                viewImage = itemView.findViewById(R.id.imageView);
            }
        }
    }

}
