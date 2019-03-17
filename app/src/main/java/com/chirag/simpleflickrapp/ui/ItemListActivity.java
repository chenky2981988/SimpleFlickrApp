package com.chirag.simpleflickrapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chirag.simpleflickrapp.R;
import com.chirag.simpleflickrapp.adapters.FlickrRecyclerViewAdapter;
import com.chirag.simpleflickrapp.interfaces.ListItemClickListener;
import com.chirag.simpleflickrapp.model.Photo;
import com.chirag.simpleflickrapp.model.SearchFlickrResponse;
import com.chirag.simpleflickrapp.network.APIClient;
import com.chirag.simpleflickrapp.network.APIInterface;
import com.chirag.simpleflickrapp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements ListItemClickListener, TextView.OnEditorActionListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.search_view)
    AppCompatEditText searchEditText;
    @BindView(R.id.item_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Photo> searchPhotoList;
    private boolean mTwoPane;
    private APIInterface apiInterface;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        searchEditText.setOnEditorActionListener(this);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        assert recyclerView != null;
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        apiInterface = APIClient.getClient().create(APIInterface.class);

        //getSearchPhoto("NewYork");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (searchPhotoList == null && !TextUtils.isEmpty(this.searchText)) {
            if (Utils.isNetworkAvailable(this)) {
                getSearchPhoto(this.searchText);
            } else {
                Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getSearchPhoto(String tag) {
        progressBar.setVisibility(View.VISIBLE);
        Call<SearchFlickrResponse> searchFlickrResponseCall = apiInterface.getSearchPhotos(tag);
        searchFlickrResponseCall.enqueue(new Callback<SearchFlickrResponse>() {
            @Override
            public void onResponse(Call<SearchFlickrResponse> call, Response<SearchFlickrResponse> response) {
                if (response != null) {
                    SearchFlickrResponse searchFlickrResponse = response.body();
                    searchPhotoList = searchFlickrResponse.getPhotos().getPhoto();
                    FlickrRecyclerViewAdapter flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(searchPhotoList);
                    flickrRecyclerViewAdapter.setClickListener(ItemListActivity.this);
                    recyclerView.setAdapter(flickrRecyclerViewAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SearchFlickrResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error in Flickr Image list dowanloading", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view, int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(ItemDetailFragment.ARG_PHOTO_DETAILS, searchPhotoList.get(position));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_PHOTO_DETAILS, searchPhotoList.get(position));
            startActivity(intent);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String searchText = textView.getText().toString();
            if (!TextUtils.isEmpty(searchText)) {
                getSearchPhoto(searchText);
                this.searchText = searchText;
                searchEditText.setText("");
                searchEditText.clearAnimation();
                hideKeyboard();
            }
            return true;
        }
        return false;
    }

    private void hideKeyboard() {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }
}
