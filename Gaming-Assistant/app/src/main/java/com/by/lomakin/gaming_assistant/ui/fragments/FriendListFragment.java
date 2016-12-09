package com.by.lomakin.gaming_assistant.ui.fragments;


import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.loaders.FriendListLoader;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendListFragment extends Fragment implements LoaderManager.LoaderCallbacks<VKList<VKApiUser>> {

    private static final int FRIEND_LIST_LOADER_ID = 1;

    public FriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public Loader<VKList<VKApiUser>> onCreateLoader(int id, Bundle args) {
        Loader<VKList<VKApiUser>> loader = null;
        if (id==FRIEND_LIST_LOADER_ID) {
            loader = new FriendListLoader(getActivity());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<VKList<VKApiUser>> loader, VKList<VKApiUser> data) {

    }

    @Override
    public void onLoaderReset(Loader<VKList<VKApiUser>> loader) {

    }
}
