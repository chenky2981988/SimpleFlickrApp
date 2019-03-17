package com.chirag.simpleflickrapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chirag Sidhiwala on 3/17/2019.
 * chirag.sidhiwala@hotmail.com
 */
public class SearchFlickrResponse implements Parcelable {

    @SerializedName("photos")
    @Expose
    private Photos photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }


    protected SearchFlickrResponse(Parcel in) {
        photos = (Photos) in.readValue(Photos.class.getClassLoader());
        stat = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(photos);
        dest.writeString(stat);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SearchFlickrResponse> CREATOR = new Parcelable.Creator<SearchFlickrResponse>() {
        @Override
        public SearchFlickrResponse createFromParcel(Parcel in) {
            return new SearchFlickrResponse(in);
        }

        @Override
        public SearchFlickrResponse[] newArray(int size) {
            return new SearchFlickrResponse[size];
        }
    };

}
