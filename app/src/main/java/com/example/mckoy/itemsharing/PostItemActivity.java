package com.example.mckoy.itemsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//this activity is where the seller gives the information of the product he wants to sell
public class PostItemActivity extends AppCompatActivity {

    private TextView mPostView;

    private EditText mItemName;
    private EditText mAddress;
    private EditText mPhoneNumber;
    private EditText mRatings;
    private EditText mDescription;

    private Button mPostButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        mPostView = (TextView) findViewById(R.id.item_view);
        mPostView.setText("This is where you will give information about the product you want to sell");

        mItemName = (EditText) findViewById(R.id.name_id);
        mAddress = (EditText) findViewById(R.id.address_id);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber_id);
        mRatings = (EditText) findViewById(R.id.rating_id);
        mDescription = (EditText) findViewById(R.id.description_id);

        mPostButton = (Button) findViewById(R.id.post_id);

        mPostButton.setOnClickListener(buttonClickListener);


        Log.i("ss", "This is where you will give information about the product you want to sell");

    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            String name = mItemName.getText().toString();
            String address = mAddress.getText().toString();
            String phoneNumber = mPhoneNumber.getText().toString();
            String rating = mRatings.getText().toString();
            String description = mDescription.getText().toString();

            Item item = new Item(name, address, phoneNumber, rating, description);
            finish();
        }
    };
}
