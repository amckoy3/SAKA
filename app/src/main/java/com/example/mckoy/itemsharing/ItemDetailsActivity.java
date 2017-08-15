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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        //Here the Item object is received from the ListOfItemsActivity when an item is clicked. In order to send and Object from one activity to another, we need to make the Item class implements Serializable interface
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

        Picasso.with(ItemDetailsActivity.this).load(mItem.getPhotourl()).into(mItemImage);      //Picasso helps in loading the image from firebase and show in an ImageView

        //Integrating Maps API where clicking on Address would create a new intent and opens up Map
        final String myString = mItem.getAddress();

        //Making the Address text view clickable in order to show the address in Map
        mAddress.setMovementMethod(LinkMovementMethod.getInstance());
        mAddress.setText(myString, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable) mAddress.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(myString));

                //creating an intent
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);        //Opens up Map
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        };
        mySpannable.setSpan(myClickableSpan, 0, myString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);     // (0, myString.length()) will underline from the first character to the last character of the address and makes it clickable

        Button buyButton = (Button)findViewById(R.id.buy_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference().child("items");
                DatabaseReference itemRef = itemsRef.child(mItem.getItemKey());
                String buyerName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                if (buyerName.length() == 0) {
                    buyerName = "Suraj";
                }
                itemRef.child("mBuyerName").setValue(buyerName);
                itemRef.child("mBuyerPhone").setValue("123456789");
            }
        });
    }
}
