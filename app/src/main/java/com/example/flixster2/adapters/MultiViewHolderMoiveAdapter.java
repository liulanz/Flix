package com.example.flixster2.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.flixster2.DetailActivity;
import com.example.flixster2.MainActivity;
import com.example.flixster2.R;
import com.example.flixster2.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MultiViewHolderMoiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Movie> movies;
    private final int BIGPOSTER = 0, SMALLPOSTER = 1;

    public MultiViewHolderMoiveAdapter(Context context, List<Movie> movies) {
        this.context= context;
        this.movies = movies;
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getRating() >5 )
            return BIGPOSTER;
        else
            return SMALLPOSTER;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case BIGPOSTER:
                View v1 = inflater.inflate(R.layout.layout_viewholder_bigposter, viewGroup, false);
                viewHolder = new ViewHolderBigPoster(v1);
                break;
            default:
                View v2 = inflater.inflate(R.layout.layout_viewholder_smallposter, viewGroup, false);
                viewHolder = new ViewHolderSmallPoster(v2);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case BIGPOSTER:
                ViewHolderBigPoster vh1 = (ViewHolderBigPoster) viewHolder;
                configureViewBigPoster(vh1, position);
                break;
            default:
                ViewHolderSmallPoster vh = (ViewHolderSmallPoster) viewHolder;
                configureDefaultView(vh, position);
                break;
        }
    }
    private void configureViewBigPoster(ViewHolderBigPoster vh, int position) {

        Movie movie = (Movie) movies.get(position);
        String rating = String.valueOf(movie.getRating());

        if (movie != null) {

            String imageUrl;
            imageUrl = movie.getBackdropPath();

            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(context).load(imageUrl)
                    .placeholder(R.drawable.progress_bar)
                    .transform(new FitCenter(), new RoundedCornersTransformation(radius, margin))
                    .into(vh.getIvPoster());
            vh.getIvIcon().setImageResource(R.drawable.yt_icon_rgb);
            vh.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // open a new activity
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });

        }
    }

    private void configureDefaultView(ViewHolderSmallPoster vh1, int position) {
        Movie movie = (Movie) movies.get(position);
        String rating = String.valueOf(movie.getRating());
        if (movie != null) {
            vh1.getTvTitle().setText(movie.getTitle());
            vh1.getTvOverview().setText(movie.getOverview());
            String imageUrl;
            //landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(context).load(imageUrl)
                    .placeholder(R.drawable.progress_bar)
                    .transform(new FitCenter(), new RoundedCornersTransformation(radius, margin))
                    .into(vh1.getIvPoster());
            vh1.getIvIcon().setImageResource(R.drawable.yt_icon_rgb);
            vh1.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // open a new activity
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });

        }
    }


    @Override
    public int getItemCount(){return movies.size(); }

    public class ViewHolderSmallPoster extends RecyclerView.ViewHolder{
        private RelativeLayout container;
        private TextView tvTitle;
        private TextView tvOverview;
        private ImageView ivPoster;
        private ImageView ivIcon;
        public ViewHolderSmallPoster(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            ivIcon = itemView.findViewById(R.id.imagePlayIcon);
        }

        public RelativeLayout getContainer() {
            return container;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvOverview() {
            return tvOverview;
        }

        public ImageView getIvPoster() {
            return ivPoster;
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }

    }
    public class ViewHolderBigPoster extends RecyclerView.ViewHolder{
        private RelativeLayout container;
        private ImageView ivPoster;
        private ImageView ivIcon;
        public ViewHolderBigPoster(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            ivIcon = itemView.findViewById(R.id.imagePlayIcon);
        }

        public RelativeLayout getContainer() {
            return container;
        }

        public ImageView getIvPoster() {
            return ivPoster;
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }
    }
}
