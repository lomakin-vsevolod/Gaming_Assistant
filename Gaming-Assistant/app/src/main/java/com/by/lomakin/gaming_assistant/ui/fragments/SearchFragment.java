package com.by.lomakin.gaming_assistant.ui.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.adapters.FriendListAdapter;
import com.by.lomakin.gaming_assistant.adapters.SearchAdapter;
import com.by.lomakin.gaming_assistant.bo.Game;
import com.by.lomakin.gaming_assistant.bo.GamesResponse;
import com.by.lomakin.gaming_assistant.loaders.SearchLoader;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<GamesResponse> {

    public static final String SEARCH_STRING = "search_string";

    private static final int SEARCH_LOADER_ID = 1;

    private LoaderManager.LoaderCallbacks<GamesResponse> loaderCallbacks;
    private ListView listView;
    private ProgressBar progressBar;
    private TextView textView;
    private SearchView searchView;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.search_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        textView = (TextView) view.findViewById(R.id.empty);
        loaderCallbacks = this;
        searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgress();
                Bundle args = new Bundle();
                args.putString(SEARCH_STRING, query);
                getLoaderManager().restartLoader(SEARCH_LOADER_ID, args, loaderCallbacks);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @Override
    public Loader<GamesResponse> onCreateLoader(int id, Bundle args) {
        Loader<GamesResponse> loader = null;
        if (id == SEARCH_LOADER_ID) {
            loader = new SearchLoader(getActivity(), args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<GamesResponse> loader, GamesResponse data) {
        if (data != null) {
            setData(data.getResults());
        } else {
            setData(null);
        }
        dismissProgress();
    }

    @Override
    public void onLoaderReset(Loader<GamesResponse> loader) {

    }


    public void setData(List<Game> games) {
        if (games != null) {
            SearchAdapter searchAdapter = new SearchAdapter(getActivity(), games);
            listView.setAdapter(searchAdapter);
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            Picasso.with(getContext()).resumeTag(getContext());
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                            Picasso.with(getContext()).pauseTag(getContext());
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                            Picasso.with(getContext()).pauseTag(getContext());
                            break;
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        } else {
            Toast.makeText(getActivity(), "Check your internet connection!", Toast.LENGTH_SHORT).show();
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }
}
