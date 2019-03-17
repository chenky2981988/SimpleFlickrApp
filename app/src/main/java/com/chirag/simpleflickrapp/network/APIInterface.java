package com.chirag.simpleflickrapp.network;

import com.chirag.simpleflickrapp.model.SearchFlickrResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Chirag Sidhiwala on 3/17/2019.
 * chirag.sidhiwala@hotmail.com
 */
public interface APIInterface {

    @GET("/services/rest/?method=flickr.photos.search")
    public Call<SearchFlickrResponse> getSearchPhotos(@Query("tags") String tags);
}
