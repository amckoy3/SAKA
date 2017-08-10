package com.example.mckoy.itemsharing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
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

        //Integrating Maps API where clicking on Address would create a new intent and opens up Map
        final String myString = mItem.getAddress();
        mAddress.setMovementMethod(LinkMovementMethod.getInstance());
        mAddress.setText(myString, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable) mAddress.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(myString));

                //creating an intent
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        };
        mySpannable.setSpan(myClickableSpan, 0, myString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
