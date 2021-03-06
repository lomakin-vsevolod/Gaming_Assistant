package com.by.lomakin.gaming_assistant.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.api.VkApiConstants;
import com.by.lomakin.gaming_assistant.ui.MainActivity;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiUsers;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ImageView imageView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView countryTextView;
    private TextView statusTextView;
    private TextView bDateTextView;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.image_logo);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        countryTextView = (TextView) view.findViewById(R.id.country);
        statusTextView = (TextView) view.findViewById(R.id.status);
        bDateTextView = (TextView) view.findViewById(R.id.bdate);
        //setSupportActionBar(toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.selectItem(R.id.categories);
            }
        });
        showProgress();
        VKRequest vkRequest = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, VkApiConstants.FIRST_NAME + "," + VkApiConstants.LAST_NAME + "," + VkApiConstants.PHOTO_400_ORIG + "," + VkApiConstants.COUNTRY + "," + VkApiConstants.STATUS + "," + VkApiConstants.BDATE));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKList<VKApiUserFull> user = (VKList<VKApiUserFull>) response.parsedModel;
                collapsingToolbarLayout.setTitle(user.get(0).first_name + " " + user.get(0).last_name);
                if (user.get(0).country != null){
                    countryTextView.setText(user.get(0).country.title);
                }
                bDateTextView.setText(user.get(0).bdate);
                //statusTextView.setText(user.get(0).activity);
                Picasso.with(getContext()).load(user.get(0).photo_400_orig).into(imageView);
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
