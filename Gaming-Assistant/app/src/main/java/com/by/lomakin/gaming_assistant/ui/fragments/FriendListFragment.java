package com.by.lomakin.gaming_assistant.ui.fragments;


import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.adapters.FriendListAdapter;
import com.by.lomakin.gaming_assistant.loaders.FriendListLoader;
import com.by.lomakin.gaming_assistant.ui.ProfileActivity;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendListFragment extends Fragment implements LoaderManager.LoaderCallbacks<VKList<VKApiUser>> {

    private static final int FRIEND_LIST_LOADER_ID = 1;
    public static final String USER_ID = "USER_ID";

    private ListView listView;
    private ProgressBar progressBar;
    private TextView textView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoaderManager.LoaderCallbacks<VKList<VKApiUser>> loaderCallbacks;

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
        listView = (ListView) view.findViewById(R.id.friends_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        textView = (TextView) view.findViewById(R.id.empty);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.friends_frame);
        loaderCallbacks = this;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(FRIEND_LIST_LOADER_ID, null, loaderCallbacks);
            }
        });
        showProgress();
        getLoaderManager().initLoader(FRIEND_LIST_LOADER_ID, null, loaderCallbacks);
    }

    @Override
    public Loader<VKList<VKApiUser>> onCreateLoader(int id, Bundle args) {
        Loader<VKList<VKApiUser>> loader = null;
        if (id == FRIEND_LIST_LOADER_ID) {
            loader = new FriendListLoader(getActivity());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<VKList<VKApiUser>> loader, VKList<VKApiUser> data) {
        setData(data);
        dismissProgress();
    }

    @Override
    public void onLoaderReset(Loader<VKList<VKApiUser>> loader) {

    }

    public void setData(VKList<VKApiUser> friendList) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (friendList != null) {
            final FriendListAdapter friendListAdapter = new FriendListAdapter(getActivity(), friendList);
            listView.setAdapter(friendListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String userId = Integer.toString(friendListAdapter.getId(position));
                    Log.d("userId",userId);
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra(USER_ID, userId);
                    startActivity(intent);
                }
            });
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
            swipeRefreshLayout.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    public void dismissProgress () {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }
}
