package com.by.lomakin.gaming_assistant.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.api.VkAuthUtils;
import com.by.lomakin.gaming_assistant.bo.Category;
import com.by.lomakin.gaming_assistant.bo.Game;
import com.by.lomakin.gaming_assistant.bo.GameResponse;
import com.by.lomakin.gaming_assistant.loaders.GameInfoLoader;
import com.by.lomakin.gaming_assistant.ui.fragments.SearchFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GameInfoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<GameResponse> {

    private static final int GAME_INFO_LOADER_ID = 1;

    private DatabaseReference databaseReference;
    private ImageView imageView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView descriptionTextView;
    private TextView developerTextView;
    private VkAuthUtils vkAuthUtils;
    private FloatingActionButton fab;
    private List<Category> categories;
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);
        imageView = (ImageView) findViewById(R.id.image_logo);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        descriptionTextView = (TextView) findViewById(R.id.desc);
        developerTextView = (TextView) findViewById(R.id.dev);
        setSupportActionBar(toolbar);
        vkAuthUtils = new VkAuthUtils(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> list = new ArrayList<Category>();
                int i = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    list.add(child.getValue(Category.class));
                    list.get(i).setId(child.getKey());
                    i++;
                }
                categories = list;
                final String strings[] = new String[list.size()];

                for (int a = 0; a < list.size(); a++) {
                    strings[a] = list.get(a).getName();
                }

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameInfoActivity.this);
                        builder.setTitle("Categories");
                        builder.setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("categories").child(categories.get(which).getId()).child("games").child(Integer.toString(game.getId())).setValue(true);
                                databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("games").child(Integer.toString(game.getId())).child("categories").child(categories.get(which).getId()).setValue(true);
                            }
                        });
                        builder.show();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bundle args = new Bundle();
        args.putString(SearchFragment.GAME_ID, getIntent().getStringExtra(SearchFragment.GAME_ID));
        getSupportLoaderManager().initLoader(GAME_INFO_LOADER_ID, args, this);
    }

    @Override
    public Loader<GameResponse> onCreateLoader(int id, Bundle args) {
        Loader<GameResponse> loader = null;
        if (id == GAME_INFO_LOADER_ID) {
            loader = new GameInfoLoader(this, args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<GameResponse> loader, GameResponse data) {
        if (data != null) {
            setData(data.getResults());
        } else {
            setData(null);
        }
    }

    @Override
    public void onLoaderReset(Loader<GameResponse> loader) {

    }

    public void setData(Game game) {
        if (game != null) {
            this.game = game;
            Picasso.with(this).load(game.getImage().getScreenUrl()).fit().into(imageView);
            collapsingToolbarLayout.setTitle(game.getName());
            descriptionTextView.setText(game.getDeck());
            if (game.getDevelopers() != null) {
                String developers = "";
                for (int i = 0; i < game.getDevelopers().size(); i++) {
                    if (i + 1 == game.getDevelopers().size()) {
                        developers = developers + game.getDevelopers().get(i).getName();
                    } else {
                        developers = developers + game.getDevelopers().get(i).getName() + ", ";
                    }
                }
                developerTextView.setText(developers);
            }


        } else {
           /* Toast.makeText(getActivity(), "Check your internet connection!", Toast.LENGTH_SHORT).show();
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);*/
        }
    }
}
