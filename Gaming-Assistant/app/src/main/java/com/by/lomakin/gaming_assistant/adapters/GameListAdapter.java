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
import com.by.lomakin.gaming_assistant.bo.Game;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nuclear on 19.12.2016.
 */

public class GameListAdapter extends BaseAdapter {

    private List<Game> games;
    private Context context;
    private DatabaseReference databaseReference;
    private VkAuthUtils vkAuthUtils;
    private boolean showDeleteButton = false;
    private String categoryId;


    public GameListAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        vkAuthUtils = new VkAuthUtils(this.context);
    }

    public GameListAdapter(Context context, List<Game> games, boolean showDeleteButton, String categoryId) {
        this(context,games);
        this.showDeleteButton=showDeleteButton;
        this.categoryId=categoryId;
    }

    private static class Holder {
        ImageView gameImage;
        TextView gameName;
        ImageButton imageButton;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return games.get(position).hashCode();
    }

    public int getId(int position) {
        return games.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_game, parent, false);
            Holder holder = new GameListAdapter.Holder();
            holder.gameName = (TextView) convertView.findViewById(R.id.game_name);
            holder.gameImage = (ImageView) convertView.findViewById(R.id.game_image);
            holder.imageButton = (ImageButton) convertView.findViewById(R.id.image_button);
            convertView.setTag(holder);
        }
        Holder h = (Holder) convertView.getTag();
        h.gameName.setText(games.get(position).getName());
        if (games.get(position).getImage() != null){
            Picasso.with(context).load(games.get(position).getImage().getIconUrl()).tag(context).into(h.gameImage);
        }
        if (showDeleteButton){
            h.imageButton.setVisibility(View.VISIBLE);
            h.imageButton.setFocusable(false);
            h.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("game_ID",Integer.toString(getId(position)));
                    databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("categories").child(categoryId).child("games").child(Integer.toString(getId(position))).removeValue();
                    databaseReference.child("users").child(vkAuthUtils.getUserIdFromSharedPreferences()).child("games").child(Integer.toString(getId(position))).child("categories").child(categoryId).removeValue();
                    games.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }
}
