package com.by.lomakin.gaming_assistant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.by.lomakin.gaming_assistant.R;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Nuclear on 10.12.2016.
 */

public class FriendListAdapter extends BaseAdapter {

    private VKList<VKApiUser> friends;
    private Context context;

    public FriendListAdapter(Context context, VKList<VKApiUser> friends) {
        this.context = context;
        this.friends = friends;
    }

    private static class Holder {
        ImageView friendImage;
        TextView friendName;
    }

    @Override
    public int getCount() {
        return friends.getCount();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return friends.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_friend, parent, false);
            Holder holder = new Holder();
            holder.friendName = (TextView) convertView.findViewById(R.id.friend_name);
            holder.friendImage = (ImageView) convertView.findViewById(R.id.friend_avatar);
            convertView.setTag(holder);
        }
        Holder h = (Holder) convertView.getTag();
        h.friendName.setText(friends.get(position).first_name + " " + friends.get(position).last_name);
        Picasso.with(context).load(friends.get(position).photo_100).tag(context).into(h.friendImage); //.transform(new RoundedCornersTransformation(100,100))
        return convertView;
    }
}
