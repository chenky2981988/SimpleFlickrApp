package com.chirag.simpleflickrapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chirag.simpleflickrapp.viewholders.FlickrRecyclerViewHolder;
import com.chirag.simpleflickrapp.R;
import com.chirag.simpleflickrapp.interfaces.ListItemClickListener;
import com.chirag.simpleflickrapp.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Chirag Sidhiwala on 3/17/2019.
 * chirag.sidhiwala@hotmail.com
 */
public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewHolder> {

    private List<Photo> photoList;
    private ListItemClickListener clickListener;

    public FlickrRecyclerViewAdapter(
            List<Photo> photo_list) {
        // this.mContext = context;
        this.photoList = photo_list;
    }

    @NonNull
    @Override
    public FlickrRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new FlickrRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrRecyclerViewHolder flickrRecyclerViewHolder, int position) {
        Photo mPhoto = photoList.get(position);
        flickrRecyclerViewHolder.mTitleTextView.setText(mPhoto.getTitle());
        flickrRecyclerViewHolder.itemView.setTag(position);
        Picasso.get().load(mPhoto.getUrlM())
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(flickrRecyclerViewHolder.mThumbnail);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void setClickListener(ListItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public ListItemClickListener getClickListener() {
        return this.clickListener;
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            getClickListener().onClick(v, position);


        }
    };

}
