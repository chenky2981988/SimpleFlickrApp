package com.chirag.simpleflickrapp.viewholders;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chirag.simpleflickrapp.R;

/**
 * Created by Chirag Sidhiwala on 3/17/2019.
 * chirag.sidhiwala@hotmail.com
 */
public class FlickrRecyclerViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView mTitleTextView;
    public AppCompatImageView mThumbnail;

    public FlickrRecyclerViewHolder(View view) {
        super(view);
        mTitleTextView = (AppCompatTextView) view.findViewById(R.id.image_title);
        mThumbnail = (AppCompatImageView) view.findViewById(R.id.thumbnail);
    }
}

