package com.example.mckoy.itemsharing;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private List<Item> mDataSource;

    public ItemAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.activity_list_of_items, parent, false);
        }
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.itemImage);
        TextView sellerName = (TextView) convertView.findViewById(R.id.sellerName);
        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        Item item = getItem(position);
        //declare how to show the image in 'itemImage' ImageView
        sellerName.setText(item.getSellerName());
        itemName.setText(item.getItemName());

        return convertView;
    }
}

