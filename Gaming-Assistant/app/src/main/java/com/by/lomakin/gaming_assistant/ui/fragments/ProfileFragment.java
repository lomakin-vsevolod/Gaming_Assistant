package com.by.lomakin.gaming_assistant.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.api.VkAuthUtils;
import com.by.lomakin.gaming_assistant.bo.Game;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


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
/*        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Games");
//        List<Game> games = new ArrayList<Game>();
        Game game = new Game(1,"test","battlefield1234",null);
//        Game game2 = new Game(2,"test2","battlefield 2",null);
//        games.add(game);
//        games.add(game2);
//        myRef.push().setValue(game);
        //VkAuthUtils vkAuthUtils = new VkAuthUtils(getActivity());
        //myRef.child("id").setValue(vkAuthUtils.getUserIdFromSharedPreferences());



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Game> list = new ArrayList<Game>();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    list.add(child.getValue(Game.class));
                }
                for (int i=0;i<list.size();i++){
                    Log.d(Integer.toString(i),list.get(i).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



    }
}
