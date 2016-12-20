package com.by.lomakin.gaming_assistant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.bo.Category;
import com.by.lomakin.gaming_assistant.bo.Game;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nuclear on 20.12.2016.
 */

public class CategoriesAdapter extends BaseAdapter {
    private List<Category> categories;
    private Context context;

    public CategoriesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    private static class Holder {
        TextView categoryName;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_category, parent, false);
            CategoriesAdapter.Holder holder = new CategoriesAdapter.Holder();
            holder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
            convertView.setTag(holder);
        }
        CategoriesAdapter.Holder h = (CategoriesAdapter.Holder) convertView.getTag();
        h.categoryName.setText(categories.get(position).getName());
        return convertView;
    }
}
