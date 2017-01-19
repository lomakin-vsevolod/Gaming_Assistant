package com.by.lomakin.gaming_assistant.ui;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.api.VkApiConstants;
import com.by.lomakin.gaming_assistant.ui.fragments.FriendListFragment;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView countryTextView;
    private TextView statusTextView;
    private TextView bDateTextView;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = (ImageView) findViewById(R.id.image_logo);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        countryTextView = (TextView) findViewById(R.id.country);
        statusTextView = (TextView) findViewById(R.id.status);
        bDateTextView = (TextView) findViewById(R.id.bdate);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        userId = getIntent().getStringExtra(FriendListFragment.USER_ID);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                intent.putExtra(FriendListFragment.USER_ID, userId);
                startActivity(intent);
            }
        });
        showProgress();
        VKRequest vkRequest = VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, userId, VKApiConst.FIELDS, VkApiConstants.FIRST_NAME + "," + VkApiConstants.LAST_NAME + "," + VkApiConstants.PHOTO_400_ORIG + "," + VkApiConstants.COUNTRY + "," + VkApiConstants.STATUS + "," + VkApiConstants.BDATE));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKList<VKApiUserFull> user = (VKList<VKApiUserFull>) response.parsedModel;
                collapsingToolbarLayout.setTitle(user.get(0).first_name + " " + user.get(0).last_name);
                if (user.get(0).country != null) {
                    countryTextView.setText(user.get(0).country.title);
                }
                bDateTextView.setText(user.get(0).bdate);
                //statusTextView.setText(user.get(0).activity);
                if (!user.get(0).photo_400_orig .equals("")) {
                    Log.d("PHOTO",user.get(0).photo_400_orig);
                    Picasso.with(getApplicationContext()).load(user.get(0).photo_400_orig).into(imageView);
                }
                dismissProgress();
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {

            }

            @Override
            public void onError(VKError error) {

            }
        });

    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);
    }

    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }
}
