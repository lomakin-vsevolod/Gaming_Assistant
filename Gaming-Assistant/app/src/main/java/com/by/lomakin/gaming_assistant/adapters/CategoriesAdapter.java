package com.by.lomakin.gaming_assistant.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.api.VkAuthUtils;
import com.by.lomakin.gaming_assistant.bo.Category;
import com.by.lomakin.gaming_assistant.bo.Game;
import com.by.lomakin.gaming_assistant.ui.fragments.CategoriesFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nuclear on 20.12.2016.
 */

public class CategoriesAdapter extends BaseAdapter {
    private List<Category> categories;
    private Context context;
    private DatabaseReference databaseReference;
    private VkAuthUtils vkAuthUtils;
    private boolean showDeleteButton = false;

    public CategoriesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        vkAuthUtils = new VkAuthUtils(this.context);
    }

    public CategoriesAdapter(Context context, List<Category> categories, boolean showDeleteButton) {
        this(context,categories);
        this.showDeleteButton=showDeleteButton;
    }

    private static class Holder {
        TextView categoryName;
        ImageButton imageButton;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).hashCode();
    }

    public String getId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_category, parent, false);
            CategoriesAdapter.Holder holder = new CategoriesAdapter.Holder();
            holder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
            holder.imageButton = (ImageButton) convertView.findViewById(R.id.image_button);
            convertView.setTag(holder);
        }
        CategoriesAdapter.Holder h = (CategoriesAdapter.Holder) convertView.getTag();
        h.categoryName.setText(categories.get(position).getName());
        if (showDeleteButton){
            h.imageButton.setVisibility(View.VISIBLE);
            h.imageButton.setFocusable(false);
            h.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("category_ID",getId(position));
                    databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("categories").child(getId(position)).removeValue();
                    categories.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }
}
