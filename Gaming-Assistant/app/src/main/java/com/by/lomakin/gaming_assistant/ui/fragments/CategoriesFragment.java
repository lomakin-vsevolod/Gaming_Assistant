package com.by.lomakin.gaming_assistant.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.adapters.CategoriesAdapter;
import com.by.lomakin.gaming_assistant.adapters.SearchAdapter;
import com.by.lomakin.gaming_assistant.api.VkAuthUtils;
import com.by.lomakin.gaming_assistant.bo.Category;
import com.by.lomakin.gaming_assistant.bo.Game;
import com.by.lomakin.gaming_assistant.ui.GameInfoActivity;
import com.by.lomakin.gaming_assistant.ui.GameListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.by.lomakin.gaming_assistant.ui.fragments.SearchFragment.GAME_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    public static final String CATEGORY_ID = "CATEGORY_ID";

    private DatabaseReference databaseReference;

    private ListView listView;
    private ProgressBar progressBar;
    private TextView textView;
    private EditText editText;
    private Button button;
    private LinearLayout linearLayout;
    private VkAuthUtils vkAuthUtils;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.category_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        textView = (TextView) view.findViewById(R.id.empty);
        editText = (EditText) view.findViewById(R.id.text_category);
        button = (Button) view.findViewById(R.id.add_category);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        vkAuthUtils = new VkAuthUtils(getActivity());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")){
                    databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("categories").push().child("name").setValue(editText.getText().toString());
                    showProgress();
                    loadData();
                }

            }
        });
        showProgress();
        loadData();
    }

    public void loadData(){
        databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> list = new ArrayList<Category>();
                int i=0;
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    list.add(child.getValue(Category.class));
                    list.get(i).setId(child.getKey());
                    i++;
                }
                setData(list);
                dismissProgress();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setData(List<Category> categories) {
        if (categories != null) {
            final CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity(), categories);
            listView.setAdapter(categoriesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String categoryId = categoriesAdapter.getId(position);
                    Log.d("catId",categoryId);
                    Intent intent = new Intent(getActivity(), GameListActivity.class);
                    intent.putExtra(CATEGORY_ID, categoryId);
                    startActivity(intent);
                }
            });

        } else {
            Toast.makeText(getActivity(), "Check your internet connection!", Toast.LENGTH_SHORT).show();
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
    }

    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }
}
