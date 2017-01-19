package com.by.lomakin.gaming_assistant.ui;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.adapters.GameListAdapter;
import com.by.lomakin.gaming_assistant.adapters.SearchAdapter;
import com.by.lomakin.gaming_assistant.api.VkAuthUtils;
import com.by.lomakin.gaming_assistant.bo.Category;
import com.by.lomakin.gaming_assistant.bo.Game;
import com.by.lomakin.gaming_assistant.bo.GameFirebase;
import com.by.lomakin.gaming_assistant.bo.GamesResponse;
import com.by.lomakin.gaming_assistant.loaders.SearchLoader;
import com.by.lomakin.gaming_assistant.ui.fragments.CategoriesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<GamesResponse> {

    public static final String SEARCH_STRING = "search_string";

    private static final int SEARCH_LOADER_ID = 1;

    private DatabaseReference databaseReference;
    private ListView listView;
    private ProgressBar progressBar;
    private TextView textView;
    private VkAuthUtils vkAuthUtils;
    private String categoryId;
    private String userId;
    private boolean showDeleteButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        listView = (ListView) findViewById(R.id.game_list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        textView = (TextView) findViewById(R.id.empty);
        vkAuthUtils = new VkAuthUtils(this);
        categoryId = getIntent().getStringExtra(CategoriesFragment.CATEGORY_ID);
        userId = getIntent().getStringExtra(CategoriesFragment.USER_ID);
        if (userId.equals(vkAuthUtils.getUserIdFromSharedPreferences())){
            showDeleteButton = true;
        }
        showProgress();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(userId).child("categories").child(categoryId).child("games").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<GameFirebase> list = new ArrayList<GameFirebase>();

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    GameFirebase gameFirebase = new GameFirebase();
                    gameFirebase.setGameId(child.getKey());
                    list.add(gameFirebase);

                }
                String ids = ",id:";
                for (GameFirebase gameFirebase : list){
                    Log.d("test",gameFirebase.getGameId());
                    ids = ids + gameFirebase.getGameId() + "|";
                }
                startLoader(ids);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("games").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<GameFirebase> list = new ArrayList<GameFirebase>();

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    GameFirebase gameFirebase = new GameFirebase();
                    gameFirebase.setGameId(child.getKey());
                    list.add(gameFirebase);

                }
                String ids = ",id:";
                for (GameFirebase gameFirebase : list){
                    Log.d("test",gameFirebase.getGameId());
                    ids = ids + gameFirebase.getGameId() + "|";
                }
                startLoader(ids);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    public void startLoader(String ids){
        Bundle args = new Bundle();
        args.putString(SEARCH_STRING, ids);
        getSupportLoaderManager().initLoader(SEARCH_LOADER_ID, args, this);
    }

    @Override
    public Loader<GamesResponse> onCreateLoader(int id, Bundle args) {
        Loader<GamesResponse> loader = null;
        if (id == SEARCH_LOADER_ID) {
            loader = new SearchLoader(this, args);
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
            final GameListAdapter gameListAdapter = new GameListAdapter(this, games, showDeleteButton,categoryId);
            listView.setAdapter(gameListAdapter);
            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int gameId = searchAdapter.getId(position);
                    Log.d("GameId",Integer.toString(gameId));
                    Intent intent = new Intent(getActivity(), GameInfoActivity.class);
                    intent.putExtra(GAME_ID, Integer.toString(gameId));
                    startActivity(intent);
                }
            });*/
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            Picasso.with(getApplicationContext()).resumeTag(getApplicationContext());
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                            Picasso.with(getApplicationContext()).pauseTag(getApplicationContext());
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                            Picasso.with(getApplicationContext()).pauseTag(getApplicationContext());
                            break;
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        } else {
            Toast.makeText(this, "Check your internet connection!", Toast.LENGTH_SHORT).show();
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
