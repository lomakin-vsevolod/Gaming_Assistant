package com.by.lomakin.gaming_assistant.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.api.VkApiConstants;
import com.by.lomakin.gaming_assistant.ui.fragments.CategoriesFragment;
import com.by.lomakin.gaming_assistant.ui.fragments.FriendListFragment;
import com.by.lomakin.gaming_assistant.ui.fragments.ProfileFragment;
import com.by.lomakin.gaming_assistant.ui.fragments.SearchFragment;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        imageView = (ImageView) header.findViewById(R.id.imageViewDrawer);
        textView = (TextView) header.findViewById(R.id.textViewDrawer);

        if (savedInstanceState == null) {
            selectItem(R.id.profile);
            navigationView.setCheckedItem(R.id.profile);
        }
        VKRequest vkRequest = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, VkApiConstants.FIRST_NAME + "," + VkApiConstants.LAST_NAME + "," + VkApiConstants.PHOTO_100));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKList<VKApiUser> user = (VKList<VKApiUser>) response.parsedModel;
                Log.d("tset",user.get(0).first_name + " " + user.get(0).last_name);
                textView.setText(user.get(0).first_name + " " + user.get(0).last_name);
                Picasso.with(getApplicationContext()).load(user.get(0).photo_100).into(imageView);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {

            }

            @Override
            public void onError(VKError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        selectItem(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void selectItem(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
            case R.id.friends:
                fragment = new FriendListFragment();
                break;
            case R.id.categories:
                fragment = new CategoriesFragment();
                break;
            case R.id.search:
                fragment = new SearchFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

    }
}
