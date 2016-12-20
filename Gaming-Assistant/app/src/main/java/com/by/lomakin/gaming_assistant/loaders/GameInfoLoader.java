package com.by.lomakin.gaming_assistant.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.by.lomakin.gaming_assistant.api.GiantBombApi;
import com.by.lomakin.gaming_assistant.bo.GameResponse;
import com.by.lomakin.gaming_assistant.bo.GamesResponse;
import com.by.lomakin.gaming_assistant.ui.fragments.SearchFragment;

/**
 * Created by Nuclear on 20.12.2016.
 */

public class GameInfoLoader extends AsyncTaskLoader<GameResponse> {
    private String gameId;

    public GameInfoLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            this.gameId = args.getString(SearchFragment.GAME_ID);
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public GameResponse loadInBackground() {
        return GiantBombApi.getGameById(gameId);
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
