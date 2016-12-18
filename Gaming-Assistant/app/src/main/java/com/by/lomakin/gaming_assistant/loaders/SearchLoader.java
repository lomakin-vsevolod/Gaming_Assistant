package com.by.lomakin.gaming_assistant.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.by.lomakin.gaming_assistant.api.GiantBombApi;
import com.by.lomakin.gaming_assistant.bo.GamesResponse;
import com.by.lomakin.gaming_assistant.ui.fragments.SearchFragment;

/**
 * Created by Nuclear on 19.12.2016.
 */

public class SearchLoader extends AsyncTaskLoader<GamesResponse> {
    private String searchString;

    public SearchLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            this.searchString = args.getString(SearchFragment.SEARCH_STRING);
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public GamesResponse loadInBackground() {
        return GiantBombApi.searchGames(searchString);
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
    }
}
