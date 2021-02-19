package com.example.flixster2.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster2.DetailActivity;
import com.example.flixster2.MainActivity;
import com.example.flixster2.R;
import com.example.flixster2.models.Movie;

import com.example.flixster2.R;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movie> movies;
    public MovieAdapter(Context context, List<Movie>movies){
        this.context= context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("MovieAdapter: ","onCreateHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent,false );
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d("MovieAdapter: ","onBindViewHolder " +position);
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            ivIcon = itemView.findViewById(R.id.imagePlayIcon);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //landscape
            if (context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }
            else{
                imageUrl = movie.getPosterPath();
            }
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(context).load(imageUrl)
                   .placeholder(R.drawable.progress_bar)
                    .transform(new FitCenter(), new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
            ivIcon.setImageResource(R.drawable.yt_icon_rgb);



            // 1.Register click listener on the whole row
            // 2. Navigate to a new activity on tap
            container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
//                    toast is a way to debug
//                    Toast.makeText(context, movie.getTitle() ,Toast.LENGTH_SHORT).show();
                    // open a new activity
                    Intent i = new Intent (context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                   // i.putExtra(DetailActivity.EXTRA_CONTACT, contact);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, tvTitle, "title");
                    // android doesn't recognize movie object by default
                    // we need to make movie object to parcelable through a library

                    context.startActivity(i, options.toBundle());

                }
            });
        }
    }




}
