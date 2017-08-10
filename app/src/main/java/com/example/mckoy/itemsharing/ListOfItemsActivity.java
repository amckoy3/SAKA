package com.example.mckoy.itemsharing;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ListOfItemsActivity extends AppCompatActivity{

    private Button mButton;
    private List<Item> mItems;
    private ListView mListView;

    //Log out button
    private Button mLogoutButton;

    //Firebase variables needed for logout
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    private static final int POST_ITEM_ACTIVITY =  5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        Toast.makeText(ListOfItemsActivity.this, "Login is successful",
                Toast.LENGTH_SHORT).show();
        mListView = (ListView) findViewById(R.id.list_view);
        ItemDataSource.get(ListOfItemsActivity.this).getItems(new ItemDataSource.ItemListener() {
            @Override
            public void onItemsReceived(List<Item> items) {
                mItems = items;
                mListView.setAdapter(new ItemAdapter(ListOfItemsActivity.this, R.layout.list_view_item, items));
            }
        });
        mButton = (Button) findViewById(R.id.button_id);

        //initializing Firebase instance variables

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(ListOfItemsActivity.this, MainActivity.class));
                }
            }
        };
        mLogoutButton = (Button) findViewById(R.id.log_out);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Firebase signout
                mAuth.signOut();

                //Google API signout
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);

            }
        });


        mButton.setOnClickListener(buttonClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST_ITEM_ACTIVITY) {
            finish();
        }
    }

    public void refresh() {
        ItemDataSource.get(ListOfItemsActivity.this).getItems(new ItemDataSource.ItemListener() {
            @Override
            public void onItemsReceived(List<Item> items) {
                mItems = items;
                mListView.setAdapter(new ItemAdapter(ListOfItemsActivity.this, R.layout.list_view_item, items));
            }
        });
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            //Intent i = new Intent(ListOfItemsActivity.this, PostItemActivity.class);
            //startActivity(i);
            startActivityForResult(new Intent(ListOfItemsActivity.this, PostItemActivity.class), POST_ITEM_ACTIVITY);
        }
    };
}
