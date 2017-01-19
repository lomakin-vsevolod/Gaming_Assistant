package com.by.lomakin.gaming_assistant.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.adapters.CategoriesAdapter;
import com.by.lomakin.gaming_assistant.api.VkAuthUtils;
import com.by.lomakin.gaming_assistant.bo.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    public static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String USER_ID = "USER_ID";

    private DatabaseReference databaseReference;

    private ListView listView;
    private ProgressBar progressBar;
    private TextView textView;

    private VkAuthUtils vkAuthUtils;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        listView = (ListView) findViewById(R.id.category_list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        textView = (TextView) findViewById(R.id.empty);

        vkAuthUtils = new VkAuthUtils(this);
        userId = getIntent().getStringExtra(USER_ID);
        Log.d("USER_ID",userId);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        showProgress();
        loadData();
    }

    public void loadData(){
        databaseReference.child("users").child(userId).child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
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
            final CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, categories,false);
            listView.setAdapter(categoriesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String categoryId = categoriesAdapter.getId(position);
                    Log.d("catId",categoryId);
                    Intent intent = new Intent(getApplicationContext(), GameListActivity.class);
                    intent.putExtra(CATEGORY_ID, categoryId);
                    intent.putExtra(USER_ID, userId);
                    startActivity(intent);
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
