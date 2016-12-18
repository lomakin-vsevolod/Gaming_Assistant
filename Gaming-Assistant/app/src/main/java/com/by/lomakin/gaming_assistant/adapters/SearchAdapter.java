package com.by.lomakin.gaming_assistant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.by.lomakin.gaming_assistant.R;
import com.by.lomakin.gaming_assistant.bo.Game;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nuclear on 19.12.2016.
 */

public class SearchAdapter extends BaseAdapter {

    private List<Game> games;
    private Context context;

    public SearchAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    private static class Holder {
        ImageView gameImage;
        TextView gameName;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_game, parent, false);
            Holder holder = new SearchAdapter.Holder();
            holder.gameName = (TextView) convertView.findViewById(R.id.game_name);
            holder.gameImage = (ImageView) convertView.findViewById(R.id.game_image);
            convertView.setTag(holder);
        }
        Holder h = (Holder) convertView.getTag();
        h.gameName.setText(games.get(position).getName());
        if (games.get(position).getImage() != null){
            Picasso.with(context).load(games.get(position).getImage().getIconUrl()).tag(context).into(h.gameImage);
        }
        return convertView;
    }
}
