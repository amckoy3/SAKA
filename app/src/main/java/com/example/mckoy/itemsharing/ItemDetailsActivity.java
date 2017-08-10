package com.example.mckoy.itemsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ItemDetailsActivity extends AppCompatActivity {
    private TextView mSellerName;
    private TextView mItemName;
    private TextView mPrice;
    private TextView mAddress;
    private TextView mRating;
    private TextView mDescription;
    private ImageView mItemImage;

    private Item mItem;     //This will get the 'Item' object from ListOfItemsActivity based on the position of ListViewItem clicked

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent i = getIntent();
        mItem = (Item)i.getSerializableExtra("itemObject");

        mSellerName = (TextView) findViewById(R.id.seller_name);
        mItemName = (TextView)findViewById(R.id.item_name);
        mPrice = (TextView) findViewById(R.id.price);
        mAddress = (TextView) findViewById(R.id.item_address);
        mRating = (TextView) findViewById(R.id.rating);
        mDescription = (TextView) findViewById(R.id.description);
        mItemImage = (ImageView) findViewById(R.id.item_image);

        mItemName.setText(mItem.getItemName());
        mSellerName.setText(mItem.getSellerName());
        mPrice.setText(mItem.getPrice());
        mAddress.setText(mItem.getAddress());
        mRating.setText(mItem.getRating());
        mDescription.setText(mItem.getDescription());

        Picasso.with(ItemDetailsActivity.this).load(mItem.getPhotourl()).into(mItemImage);
    }
}
