package com.example.android.popularmovies;

import android.content.Context;
import android.view.View.OnClickListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Ahmed on 3/5/2017.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PosterViewHolder> {

    private String[] posters;

    Context context;

    private final PMAdapterOnClickHandler mClickHandler;

    public interface PMAdapterOnClickHandler
    {
        void onClick(int moviePos);
    }

    public PopularMoviesAdapter(PMAdapterOnClickHandler clickHandler)
    {
        mClickHandler = clickHandler;
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
         public final ImageView mPosterView;

        public PosterViewHolder(View view) {
            super(view);
            mPosterView = (ImageView) view.findViewById(R.id.poster_view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPos = getAdapterPosition();
            mClickHandler.onClick(adapterPos);
        }
    }

    public void setPostersData(Context cont,String[] postersData)
    {
        posters = postersData;
        context=cont;
        notifyDataSetChanged();
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForPoster = R.layout.poster;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForPoster, viewGroup, false);

        PosterViewHolder posterViewHolder = new PosterViewHolder(view);

        return posterViewHolder;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder viewHolder, int position) {

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/"+posters[position])
                .resize(480,640)
                .into(viewHolder.mPosterView);
    }

    @Override
    public int getItemCount() {
        if(posters==null)
            return 0;
        return posters.length;
    }


}
